package com.xht.boot.oauth2.entity;


import java.util.Set;

public class OAuth2ClientCredentialsGrantAuthorization extends OAuth2AuthorizationGrantAuthorization {

    public OAuth2ClientCredentialsGrantAuthorization(String id, String registeredClientId, String principalName,
                                                     Set<String> authorizedScopes, AccessToken accessToken) {
        super(id, registeredClientId, principalName, authorizedScopes, accessToken, null);
    }

}