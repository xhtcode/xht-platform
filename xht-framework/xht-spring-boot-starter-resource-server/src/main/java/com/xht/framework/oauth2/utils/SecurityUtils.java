package com.xht.framework.oauth2.utils;

import com.xht.framework.core.enums.UserTypeEnums;
import com.xht.framework.core.exception.UtilException;
import com.xht.framework.security.core.userdetails.BasicUserDetails;
import com.xht.framework.security.exception.BasicAuthenticationException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;
import java.util.Optional;

/**
 * 安全工具类
 *
 * @author xht
 **/
public final class SecurityUtils {

    private SecurityUtils() {
        throw new UtilException("This is a utility class and cannot be instantiated");
    }

    /**
     * 获取 spring security 当前的用户认证信息
     *
     * @return 当前用户认证信息
     */
    public static Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 匿名接口直接返回
        if (authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }
        return authentication;
    }

    /**
     * 判断当前是否登录
     *
     * @return 当前是否登录 状态
     */
    public static boolean isLogin() {
        return Objects.nonNull(getAuthentication());
    }


    /**
     * 获取 spring security 当前登录的用户
     */
    public static BasicUserDetails getUser() {
        return Optional.ofNullable(getAuthentication()).map(authentication -> {
            Object principal = authentication.getPrincipal();
            if (principal instanceof BasicUserDetails userDetails) {
                return userDetails;
            }
            throw new BasicAuthenticationException("用户认证信息不存在");
        }).orElseThrow(() -> new BasicAuthenticationException("用户认证信息不存在"));
    }

    /**
     * 获取 spring security 当前登录的账号
     *
     * @return 当前登录的账号
     */
    public static String getUserName() {
        return getUser().getUsername();
    }

    /**
     * 获取 spring security 当前登录的用户id
     */
    public static Long getUserId() {
        return getUser().getUserId();
    }

    /**
     * 判断当前登录用户是否为管理员
     */
    public static Boolean isAdmin() {
        return Objects.equals(getUser().getUserType(), UserTypeEnums.ADMIN);
    }


}
