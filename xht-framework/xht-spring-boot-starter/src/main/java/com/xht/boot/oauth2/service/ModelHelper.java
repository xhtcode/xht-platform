package com.xht.boot.oauth2.service;

import com.xht.boot.oauth2.entity.RedisOAuth2Authorization;
import com.xht.boot.oauth2.entity.token.BasicToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2DeviceCode;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2UserCode;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;

import java.security.Principal;
import java.util.Objects;

/**
 * @author xht
 **/
public class ModelHelper {

    public static RedisOAuth2Authorization convert(OAuth2Authorization authorization) {
        RedisOAuth2Authorization redisAuthorization = new RedisOAuth2Authorization();
        redisAuthorization.setId(authorization.getId());
        redisAuthorization.setRegisteredClientId(authorization.getRegisteredClientId());
        redisAuthorization.setPrincipalName(authorization.getPrincipalName());
        redisAuthorization.setAuthorizationGrantType(authorization.getAuthorizationGrantType().getValue());
        redisAuthorization.setAuthorizedScopes(authorization.getAuthorizedScopes());
        redisAuthorization.setAttributes(authorization.getAttributes().toString());
        redisAuthorization.setPrincipal(authorization.getAttribute(Principal.class.getName()));
        redisAuthorization.setAccessToken(extractAccessToken(authorization.getAccessToken()));
        redisAuthorization.setRefreshToken(extractRefreshToken(authorization.getRefreshToken()));
        redisAuthorization.setOidcToken(extractIdToken(authorization.getToken(OidcIdToken.class)));
        redisAuthorization.setDeviceCode(extractDeviceCode(authorization.getToken(OAuth2DeviceCode.class)));
        redisAuthorization.setUserCode(extractUserCode(authorization.getToken(OAuth2UserCode.class)));
        redisAuthorization.setAuthorizationCode(extractAuthorizationCode(authorization.getToken(OAuth2AuthorizationCode.class)));
        redisAuthorization.setRequestedScopes(authorization.getAttribute(OAuth2ParameterNames.SCOPE));
        redisAuthorization.setState(authorization.getAttribute(OAuth2ParameterNames.STATE));
        return redisAuthorization;
    }

    private static BasicToken extractAuthorizationCode(OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCodeToken) {
        if (Objects.isNull(authorizationCodeToken)) return null;
        BasicToken basicToken = new BasicToken();
        OAuth2AuthorizationCode token = authorizationCodeToken.getToken();
        basicToken.setTokenValue(token.getTokenValue());
        basicToken.setIssuedAt(token.getIssuedAt());
        basicToken.setExpiresAt(token.getExpiresAt());
        basicToken.setInvalidated(authorizationCodeToken.isInvalidated());
        return basicToken;
    }

    private static BasicToken extractUserCode(OAuth2Authorization.Token<OAuth2UserCode> userCodeToken) {
        if (Objects.isNull(userCodeToken)) return null;
        BasicToken basicToken = new BasicToken();
        OAuth2UserCode token = userCodeToken.getToken();
        basicToken.setTokenValue(token.getTokenValue());
        basicToken.setIssuedAt(token.getIssuedAt());
        basicToken.setExpiresAt(token.getExpiresAt());
        basicToken.setInvalidated(userCodeToken.isInvalidated());
        return basicToken;
    }

    private static BasicToken extractDeviceCode(OAuth2Authorization.Token<OAuth2DeviceCode> deviceCodeToken) {
        if (Objects.isNull(deviceCodeToken)) return null;
        BasicToken basicToken = new BasicToken();
        OAuth2DeviceCode token = deviceCodeToken.getToken();
        basicToken.setTokenValue(token.getTokenValue());
        basicToken.setIssuedAt(token.getIssuedAt());
        basicToken.setExpiresAt(token.getExpiresAt());
        basicToken.setInvalidated(deviceCodeToken.isInvalidated());
        return basicToken;
    }

    private static BasicToken extractIdToken(OAuth2Authorization.Token<OidcIdToken> idTokenToken) {
        if (Objects.isNull(idTokenToken)) return null;
        BasicToken basicToken = new BasicToken();
        OidcIdToken token = idTokenToken.getToken();
        basicToken.setTokenValue(token.getTokenValue());
        basicToken.setIssuedAt(token.getIssuedAt());
        basicToken.setExpiresAt(token.getExpiresAt());
        basicToken.setInvalidated(idTokenToken.isInvalidated());
        basicToken.setClaims(new BasicToken.ClaimsHolder(idTokenToken.getClaims()));
        return basicToken;
    }

    private static BasicToken extractRefreshToken(OAuth2Authorization.Token<OAuth2RefreshToken> refreshToken) {
        if (Objects.isNull(refreshToken)) return null;
        OAuth2RefreshToken token = refreshToken.getToken();
        BasicToken basicToken = new BasicToken();
        basicToken.setTokenValue(token.getTokenValue());
        basicToken.setIssuedAt(token.getIssuedAt());
        basicToken.setExpiresAt(token.getExpiresAt());
        basicToken.setInvalidated(refreshToken.isInvalidated());
        return basicToken;
    }

    private static BasicToken extractAccessToken(OAuth2Authorization.Token<OAuth2AccessToken> accessToken) {
        if (Objects.isNull(accessToken)) return null;
        BasicToken basicToken = new BasicToken();
        OAuth2TokenFormat tokenFormat = null;
        if (OAuth2TokenFormat.SELF_CONTAINED.getValue().equals(accessToken.getMetadata(OAuth2TokenFormat.class.getName()))) {
            tokenFormat = OAuth2TokenFormat.SELF_CONTAINED;
        } else if (OAuth2TokenFormat.REFERENCE.getValue().equals(accessToken.getMetadata(OAuth2TokenFormat.class.getName()))) {
            tokenFormat = OAuth2TokenFormat.REFERENCE;
        }
        OAuth2AccessToken token = accessToken.getToken();
        basicToken.setTokenValue(token.getTokenValue());
        basicToken.setTokenType(token.getTokenType());
        basicToken.setIssuedAt(token.getIssuedAt());
        basicToken.setExpiresAt(token.getExpiresAt());
        basicToken.setInvalidated(accessToken.isInvalidated());
        basicToken.setScopes(token.getScopes());
        basicToken.setTokenFormat(tokenFormat);
        basicToken.setClaims(new BasicToken.ClaimsHolder(accessToken.getClaims()));
        return basicToken;
    }
}
