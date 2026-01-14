package com.xht.auth.authentication.dao.impl;

import com.xht.api.system.domain.vo.UserLoginVo;
import com.xht.auth.authentication.dao.IAuthenticationDao;
import com.xht.auth.authentication.dao.mapper.AuthenticationMapper;
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
    public UserLoginVo findByUsernameAndLoginType(String userName, LoginTypeEnums loginType) {
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
}
