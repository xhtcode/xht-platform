package com.xht.framework.exception;

import com.xht.framework.exception.core.ErrorCode;

/**
 * 描述： 文件异常类
 *
 * @author xht
 **/
public class FileException extends BusinessException {

    public FileException(ErrorCode errorCode) {
        super(errorCode);
    }
}
