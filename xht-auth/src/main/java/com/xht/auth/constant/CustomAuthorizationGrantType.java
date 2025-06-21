package com.xht.auth.constant;

import org.springframework.security.oauth2.core.AuthorizationGrantType;

/**
 * @author xht
 **/
public interface CustomAuthorizationGrantType {


    /**
     * 密码模式
     */
    AuthorizationGrantType PASSWORD = new AuthorizationGrantType("password");


}
