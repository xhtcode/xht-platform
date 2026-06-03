package com.xht.auth.security.oauth2.server.authorization.authentication;

import org.springframework.security.oauth2.core.OAuth2ErrorCodes;

/**
 * 描述：oauth2 拒绝授权异常
 *
 * @author xht
 **/
public class OAuth2AuthorizationCancelApproveRequestAuthenticationException extends RuntimeException {

    public OAuth2AuthorizationCancelApproveRequestAuthenticationException() {
        super(OAuth2ErrorCodes.INVALID_REQUEST);
    }
}
