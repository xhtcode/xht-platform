package com.xht.auth.captcha.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 自定义验证码异常
 *
 * @author xht
 **/
public class CaptchaException extends AuthenticationException {


    /**
     * Constructs an {@code AuthenticationException} with the specified message and no
     * root cause.
     *
     * @param msg the detail message
     */
    public CaptchaException(String msg) {
        super(msg);
    }


}
