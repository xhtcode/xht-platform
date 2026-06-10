package com.xht.modules.admin.login.exception;

import com.xht.framework.exception.BusinessException;
import com.xht.framework.oauth2.token.response.Oauth2ErrorResponse;
import lombok.Getter;

/**
 * 登录异常
 *
 * @author xht
 **/
@Getter
public class LoginException extends BusinessException {

    private final Oauth2ErrorResponse errorResponse;

    public LoginException(String message) {
        super(message);
        this.errorResponse = new Oauth2ErrorResponse();
    }

    public LoginException(Oauth2ErrorResponse errorResponse) {
        super(errorResponse.getCode(), errorResponse.getErrorDescription());
        this.errorResponse = errorResponse;
    }
}
