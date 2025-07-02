package com.xht.framework.security.exception;

import org.springframework.security.access.AccessDeniedException;

import java.io.Serial;

/**
 * 内部认证异常
 *
 * @author xht
 */
public class InnerAuthException extends AccessDeniedException {

    @Serial
    private static final long serialVersionUID = 1L;

    public InnerAuthException(String message) {
        super(message);
    }
}
