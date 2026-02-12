package com.xht.auth.authentication.dao.impl;

import com.xht.auth.authentication.dao.IAuthenticationDao;
import com.xht.auth.authentication.dao.mapper.AuthenticationMapper;
import com.xht.auth.authentication.dto.Oauth2ClientDTO;
import com.xht.auth.authentication.dto.UserLoginDTO;
import com.xht.framework.core.enums.LoginTypeEnums;
import com.xht.framework.core.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 认证数据访问接口实现
 *
 * @author xht
 **/
@Slf4j
@Repository
@RequiredArgsConstructor
public class AuthenticationDaoImpl implements IAuthenticationDao {

    private final AuthenticationMapper authenticationMapper;

    /**
     * 根据用户名和登录类型查询用户信息
     *
     * @param userName  用户名
     * @param loginType 登录类型
     * @return 用户信息
     */
    @Override
    public UserLoginDTO findByUsernameAndLoginType(String userName, LoginTypeEnums loginType) {
        return authenticationMapper.findByUsernameAndLoginType(userName, Objects.requireNonNullElse(loginType, LoginTypeEnums.PASSWORD).getValue());
    }

    /**
     * 根据用户ID查询用户拥有的角色编码集合
     *
     * @param userId 用户ID，用于查询对应用户的角色信息
     * @return 返回该用户拥有的所有角色编码的Set集合，如果用户不存在或无角色则返回空集合
     */
    @Override
    public Set<String> findRoleCodesByUserId(Long userId) {
        Set<String> roleCodes = authenticationMapper.findRoleCodesByUserId(userId);
        return Optional.ofNullable(roleCodes).orElse(Collections.emptySet()).stream().filter(StringUtils::hasText).collect(Collectors.toSet());
    }

    /**
     * 根据用户ID查询用户拥有的菜单编码集合
     *
     * @param userId 用户ID，用于标识要查询的用户
     * @return 包含该用户所有权限菜单编码的Set集合，如果用户不存在或无权限则返回空集合
     */
    @Override
    public Set<String> findMenuCodesByUserId(Long userId) {
        Set<String> menuCodes = authenticationMapper.findMenuCodesByUserId(userId);
        return Optional.ofNullable(menuCodes).orElse(Collections.emptySet()).stream().filter(StringUtils::hasText).collect(Collectors.toSet());
    }

    /**
     * 根据客户端标识查询客户端信息
     *
     * @param clientId 客户端标识
     * @return 客户端信息
     */
    @Override
    public Oauth2ClientDTO findClientDetailsById(String clientId) {
        log.debug("根据客户端标识:`{}`查询客户端", clientId);
        return authenticationMapper.findClientDetailsById(clientId);
    }

    /**
     * 根据手机号查询用户信息是否存在
     *
     * @param phone 手机号
     */
    @Override
    public boolean existsUserByPhone(String phone) {
        return authenticationMapper.existsUserByPhone(phone) > 0L;
    }

}
