package com.xht.framework.core.exception;

/**
 * 工具栏异常
 *
 * @author xht
 **/
public class UtilException extends RuntimeException {

    /**
     * 构造函数
     *
     * @param message 异常信息
     */
    public UtilException(String message) {
        super(message);
    }

    public UtilException(String message, Throwable cause) {
        super(message, cause);
    }
}
