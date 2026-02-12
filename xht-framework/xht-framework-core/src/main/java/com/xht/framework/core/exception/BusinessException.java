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
public class BusinessException extends RuntimeException {

    /**
     * 错误码
     */
    private final int code;


    public BusinessException(Throwable cause) {
        super(cause);
        this.code = RConstants.FAIL;
    }

    public BusinessException(String message) {
        super(message);
        this.code = RConstants.FAIL;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

}
