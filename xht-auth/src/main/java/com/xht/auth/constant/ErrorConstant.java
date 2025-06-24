package com.xht.auth.constant;

/**
 * 错误常量
 *
 * @author xht
 **/
public interface ErrorConstant {
    /**
     * 验证码认证失败错误提示
     */
    String ERROR_MSG_CAPTCHA_AUTHENTICATION = "认证失败：验证码错误.";

    /**
     * 用户名或密码错误错误提示
     */
    String ERROR_MSG_PASSWORD_ERROR = "认证失败：用户名或密码错误.";
}
