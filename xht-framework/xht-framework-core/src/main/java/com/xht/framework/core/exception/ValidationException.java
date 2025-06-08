package com.xht.framework.core.exception;

import lombok.Getter;

/**
 * 验证异常
 *
 * @author xht
 **/
@Getter
public class ValidationException extends RuntimeException {

    private final String field;

    private final String message;

    public ValidationException(String field, String message) {
        super(message);
        this.field = field;
        this.message = message;
    }

}
