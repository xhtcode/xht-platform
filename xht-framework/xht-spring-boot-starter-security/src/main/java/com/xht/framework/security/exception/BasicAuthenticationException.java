package com.xht.framework.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 自定义授权认证异常
 *
 * @author xht
 **/
@SuppressWarnings("unused")
public class BasicAuthenticationException extends AuthenticationException {

    public BasicAuthenticationException(String msg) {
        super(msg);
    }

    public BasicAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
