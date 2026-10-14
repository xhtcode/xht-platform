package com.xht.framework.file.exception;

import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.core.ErrorCode;

/**
 * 文件存储异常
 *
 * @author xht
 */
public class FileStorageException extends BusinessException {

    /**
     * 构造器：传入ErrorCode错误码对象
     * 推荐日常业务使用，统一使用预定义错误枚举，规范错误码与提示文案；
     * 错误码与提示信息均取自错误枚举
     *
     * @param errorCode 实现ErrorCode接口的错误码枚举对象
     */
    public FileStorageException(ErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * 构造器：传入ErrorCode错误码对象 + 原始异常对象
     * 用于捕获底层异常后包装为业务异常，保留原始堆栈，同时使用规范错误码；
     * 错误码与提示信息均取自错误枚举
     *
     * @param errorCode 实现ErrorCode接口的错误码枚举对象
     * @param cause     底层原始异常
     */
    public FileStorageException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
