package com.xht.platform.file.utils;

import com.xht.framework.oauth2.utils.SecurityUtils;

import java.util.Optional;

/**
 * 描述： 当前登录用户工具类
 * <p>
 * 封装 {@link SecurityUtils}，在非 Web 上下文或异步线程（SecurityContext 不传播）中
 * 取不到用户时安全降级为 {@link Optional#empty()}，避免向上抛出异常
 *
 * @author xht
 **/
public abstract class CurrentUserHelper {

    private CurrentUserHelper() {
    }

    /**
     * 获取当前登录用户名
     *
     * @return 当前登录用户名，未登录/取不到时为 empty
     */
    public static Optional<String> getCurrentUsername() {
        try {
            return Optional.ofNullable(SecurityUtils.getUserName());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * 获取当前登录用户ID
     *
     * @return 当前登录用户ID，未登录/取不到时为 empty
     */
    public static Optional<Long> getCurrentUserId() {
        try {
            return Optional.ofNullable(SecurityUtils.getUserId());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * 判断当前登录用户是否为超级管理员
     *
     * @return true：超级管理员；非管理员或取不到用户信息时为 false
     */
    public static boolean isAdmin() {
        try {
            return Boolean.TRUE.equals(SecurityUtils.isAdmin());
        } catch (Exception e) {
            return false;
        }
    }

}
