package com.xht.cloud.security.dao;

import com.xht.cloud.oauth2.dto.UserInfoDTO;
import com.xht.cloud.security.feign.RemoteUserService;
import com.xht.framework.core.domain.R;
import com.xht.framework.core.utils.ROptional;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.security.constant.SecurityConstant;
import com.xht.framework.security.constant.enums.LoginTypeEnums;
import com.xht.framework.security.core.userdetails.BasicUserDetails;
import com.xht.framework.security.core.userdetails.UserDetailsDao;
import com.xht.framework.security.properties.SecurityProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 远程用户信息获取器
 *
 * @author xht
 **/
@Slf4j
@RequiredArgsConstructor
public class RemoteUserDetailsDao implements UserDetailsDao {

    private final RemoteUserService remoteUserService;

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
        // 远程获取用户信息
        R<UserInfoDTO> userDetailsR = remoteUserService.loadUserByUsername(username, loginType);
        UserInfoDTO userInfo = ROptional.of(userDetailsR)
                .orElseThrow(() -> new UsernameNotFoundException("远程获取用户信息失败"));
        // 构建权限集合
        Set<String> authoritiesSet = buildAuthoritiesSet(userInfo);
        // 转换为Spring Security所需的权限对象
        Set<GrantedAuthority> authorities = createAuthorityList(authoritiesSet);
        // 构建并返回用户详情对象
        return new BasicUserDetails(
                userInfo.getId(),
                userInfo.getDeptId(),
                userInfo.getUserName(),
                securityProperties.buildSalt(userInfo.getPassWord(), userInfo.getSalt()),
                userInfo.getMobile(),
                true,
                authorities
        );
        // @formatter:on
    }

    /**
     * 将权限字符串集合转换为GrantedAuthority对象列表
     *
     * @param authorities 权限字符串集合，可为null或空集合
     * @return 转换后的GrantedAuthority列表，若输入为null则返回空列表
     */
    public Set<GrantedAuthority> createAuthorityList(Set<String> authorities) {
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
     * @param userInfo 用户信息DTO
     * @return 包含角色前缀的权限集合
     */
    private Set<String> buildAuthoritiesSet(UserInfoDTO userInfo) {
        Set<String> authoritiesSet = new HashSet<>();
        // 添加角色权限（带角色前缀）
        List<String> roleCodes = userInfo.getRoleCodes();
        if (!CollectionUtils.isEmpty(roleCodes)) {
            roleCodes.forEach(role -> authoritiesSet.add(SecurityConstant.ROLE_PREFIX + role));
        }
        // 添加功能权限
        List<String> permissionCodes = userInfo.getPermissionCodes();
        if (!CollectionUtils.isEmpty(permissionCodes)) {
            authoritiesSet.addAll(permissionCodes);
        }

        return authoritiesSet;
    }
}
