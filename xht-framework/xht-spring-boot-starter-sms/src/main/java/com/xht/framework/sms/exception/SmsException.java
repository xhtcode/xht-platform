package com.xht.framework.sms.exception;

import com.xht.framework.exception.BusinessException;

/**
 * 短信异常
 *
 * @author xht
 **/
public class SmsException extends BusinessException {

    public SmsException(String message) {
        super(message);
    }

}
