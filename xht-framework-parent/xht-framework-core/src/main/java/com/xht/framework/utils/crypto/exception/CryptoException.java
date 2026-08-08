package com.xht.framework.utils.crypto.exception;

/**
 * 描述：加密异常
 *
 * @author xht
 **/
public class CryptoException extends RuntimeException {

    public CryptoException() {
    }

    public CryptoException(String message) {
        super(message);
    }

    public CryptoException(String message, Throwable cause) {
        super(message, cause);
    }

    public CryptoException(Throwable cause) {
        super(cause);
    }

}
