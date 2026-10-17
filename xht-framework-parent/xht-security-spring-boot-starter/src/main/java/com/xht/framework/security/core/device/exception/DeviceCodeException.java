package com.xht.framework.security.core.device.exception;

import java.io.Serial;

/**
 * 描述： 设备验证码异常
 *
 * @author xht
 **/
public class DeviceCodeException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public DeviceCodeException(String message) {
        super(message);
    }
}
