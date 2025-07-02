package com.xht.boot.oauth2.entity;


import java.util.Set;

/**
 * @author xht
 **/
public class OAuth2DefaultGrantAuthorization extends OAuth2AuthorizationGrantAuthorization {


    public OAuth2DefaultGrantAuthorization(String id, String registeredClientId, String principalName, Set<String> authorizedScopes, AccessToken accessToken, RefreshToken refreshToken) {
        super(id, registeredClientId, principalName, authorizedScopes, accessToken, refreshToken);
    }
}
