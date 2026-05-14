package com.xht.auth.security.web.authentication.qr.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 自定义二维码登录异常
 *
 * @author xht
 **/
public class QyLoginException extends AuthenticationException {


    public QyLoginException(String msg) {
        super(msg);
    }
}
