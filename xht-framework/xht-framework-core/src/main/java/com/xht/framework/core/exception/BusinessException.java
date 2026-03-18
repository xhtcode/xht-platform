package com.xht.framework.core.exception;

import com.xht.framework.core.constant.basic.RConstants;
import com.xht.framework.core.exception.code.ErrorCode;
import lombok.Getter;

/**
 * 自定义业务异常
 *
 * @author xht
 */
@Getter
public class BusinessException extends RuntimeException implements ErrorCode {

    /**
     * 错误码
     */
    private final int code;

    /**
     * 异常描述
     */
    private final String msg;


    public BusinessException(String message) {
        super(message);
        this.code = RConstants.FAIL;
        this.msg = message;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.code = RConstants.FAIL;
        this.msg = message;
    }


    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }

}
