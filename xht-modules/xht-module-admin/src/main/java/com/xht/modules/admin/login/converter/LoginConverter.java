package com.xht.modules.admin.login.converter;

import com.xht.framework.core.exception.UtilException;
import com.xht.framework.oauth2.token.response.Oauth2TokenResponse;
import com.xht.modules.admin.login.domain.response.LoginResponse;

/**
 * 登录转换器
 *
 * @author xht
 **/
public class LoginConverter {

    private LoginConverter() {
        throw new UtilException("This is a utility class and cannot be instantiated");
    }

    public static LoginResponse converter(Oauth2TokenResponse tokenResponse) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(tokenResponse.getAccessToken());
        loginResponse.setRefreshToken(tokenResponse.getRefreshToken());
        loginResponse.setExpiresIn(tokenResponse.getExpiresIn());
        return loginResponse;
    }

}
