package com.xht.framework.security;

import com.xht.framework.core.exception.UtilException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
     * 获取 spring security 当前的用户认证信息
     *
     * @return 当前用户认证信息
     */
    public static Optional<Authentication> getOptAuthentication() {
        return Optional.ofNullable(getAuthentication());
    }

    /**
     * 获取 spring security 当前的用户名
     *
     * @return 当前用户名
     */
    public static String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }
        if (getOptAuthentication().isPresent()) {
            return authentication.getName();
        }
        return null;
    }
}
