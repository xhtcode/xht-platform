package com.xht.auth.security.core.userdetails;

import com.xht.auth.authentication.dto.UserLoginDTO;
import com.xht.auth.authentication.dao.IAuthenticationDao;
import com.xht.framework.core.enums.LoginTypeEnums;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.security.constant.SecurityConstant;
import com.xht.framework.security.core.userdetails.BasicUserDetails;
import com.xht.framework.security.core.userdetails.BasicUserDetailsService;
import com.xht.framework.security.properties.SecurityProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 远程用户信息获取器
 *
 * @author xht
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class Oauth2UserDetailsService extends BasicUserDetailsService {

    private final IAuthenticationDao authenticationDao;

    private final SecurityProperties securityProperties;

    /**
     * 根据用户名和登录类型查询用户信息
     *
     * @param username  用户名
     * @param loginType 登录类型
     * @return 用户信息
     * @throws UsernameNotFoundException 异常
     */
    @Override
    public BasicUserDetails loadUserByUsername(String username, LoginTypeEnums loginType) throws UsernameNotFoundException {
        UserLoginDTO loginVo = authenticationDao.findByUsernameAndLoginType(username, loginType);
        if (loginVo == null) {
            throw new UsernameNotFoundException("用户不存在.");
        }
        Set<String> roleCodes = authenticationDao.findRoleCodesByUserId(loginVo.getId());
        Set<String> menuCodes = authenticationDao.findMenuCodesByUserId(loginVo.getId());
        loginVo.setRoleCodes(roleCodes);
        loginVo.setMenuButtonCodes(menuCodes);
        return convert(loginVo, loginType);
    }

    /**
     * 将远程获取的用户信息转换为Spring Security所需的用户详情对象
     *
     * @param loginVo   远程获取的用户信息响应对象，包含用户详细信息
     * @param loginType 登录类型枚举，标识用户的登录方式
     * @return 转换后的BasicUserDetails对象，用于Spring Security认证授权
     * @throws UsernameNotFoundException 当远程获取用户信息失败时抛出此异常
     */
    private BasicUserDetails convert(UserLoginDTO loginVo, LoginTypeEnums loginType) {
        // @formatter:off
        // 构建权限集合
        Set<String> authoritiesSet = buildAuthoritiesSet(loginVo);
        // 转换为Spring Security所需的权限对象
        Set<GrantedAuthority> authorities = createAuthorityList(authoritiesSet);
        // 构建并返回用户详情对象
        BasicUserDetails basicUserDetails = new BasicUserDetails(loginVo.getId(),
                loginVo.getUserType(),
                loginVo.getUserName(),
                loginVo.getNickName(),
                securityProperties.buildSalt(loginVo.getPassWord(), loginVo.getPassWordSalt()),
                authorities
        );
        basicUserDetails.setUserStatus(loginVo.getUserStatus());
        basicUserDetails.setUserPhone(loginVo.getUserPhone());
        basicUserDetails.setDeptId(loginVo.getDeptId());
        basicUserDetails.setDeptName(loginVo.getDeptName());
        basicUserDetails.setRoleCodes(loginVo.getRoleCodes());
        basicUserDetails.setMenuButtonCodes(loginVo.getMenuButtonCodes());
        basicUserDetails.setLoginType(loginType);
        return basicUserDetails;
        // @formatter:on
    }

    /**
     * 将权限字符串集合转换为GrantedAuthority对象列表
     *
     * @param authorities 权限字符串集合，可为null或空集合
     * @return 转换后的GrantedAuthority列表，若输入为null则返回空列表
     */
    private Set<GrantedAuthority> createAuthorityList(Set<String> authorities) {
        // @formatter:off
        if (authorities == null) {
            return Collections.emptySet();
        }
        return authorities.stream()
                .filter(StringUtils::hasText)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        // @formatter:on
    }

    /**
     * 构建用户权限集合（包含角色和权限）
     *
     * @param loginVo 用户信息
     * @return 包含角色前缀的权限集合
     */
    private Set<String> buildAuthoritiesSet(UserLoginDTO loginVo) {
        Set<String> authoritiesSet = new HashSet<>();
        // 添加角色权限（带角色前缀）
        Set<String> roleCodes = loginVo.getRoleCodes();
        if (!CollectionUtils.isEmpty(roleCodes)) {
            roleCodes.forEach(role -> authoritiesSet.add(SecurityConstant.ROLE_PREFIX + role));
        }
        // 添加功能权限
        Set<String> menuButtonCodes = loginVo.getMenuButtonCodes();
        if (!CollectionUtils.isEmpty(menuButtonCodes)) {
            authoritiesSet.addAll(menuButtonCodes);
        }
        return authoritiesSet;
    }

}
