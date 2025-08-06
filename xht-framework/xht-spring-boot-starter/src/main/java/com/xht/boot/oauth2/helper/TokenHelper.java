package com.xht.boot.oauth2.helper;

import com.xht.boot.oauth2.entity.token.BasicToken;
import com.xht.boot.oauth2.entity.token.ClaimsHolder;
import com.xht.framework.core.exception.UtilException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2DeviceCode;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2UserCode;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * token helper
 *
 * @author xht
 **/
@Slf4j
public final class TokenHelper {
    //OAuth2Authorization
    private TokenHelper() {
        throw new UtilException("禁止实例化此类");
    }

    public static Optional<BasicToken> authorizationCode(OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode) {
        if (Objects.isNull(authorizationCode)) {
            return Optional.empty();
        }
        OAuth2AuthorizationCode token = authorizationCode.getToken();
        BasicToken basicToken = new BasicToken();
        basicToken.setValue(token.getTokenValue());
        basicToken.setType(null);
        basicToken.setIssuedAt(token.getIssuedAt());
        basicToken.setExpiresAt(token.getExpiresAt());
        basicToken.setInvalidated(authorizationCode.isInvalidated());
        basicToken.setScopes(null);
        basicToken.setFormat(null);
        basicToken.setClaims(mapToClaimsHolder(authorizationCode.getClaims()));
        basicToken.setMetadata(mapToClaimsHolder(authorizationCode.getMetadata()));
        return Optional.of(basicToken);
    }

    public static Optional<BasicToken> accessToken(OAuth2Authorization.Token<OAuth2AccessToken> accessToken) {
        if (Objects.isNull(accessToken)) {
            return Optional.empty();
        }
        OAuth2AccessToken token = accessToken.getToken();
        BasicToken basicToken = new BasicToken();
        basicToken.setValue(token.getTokenValue());
        basicToken.setType(token.getTokenType());
        basicToken.setIssuedAt(token.getIssuedAt());
        basicToken.setExpiresAt(token.getExpiresAt());
        basicToken.setInvalidated(accessToken.isInvalidated());
        basicToken.setScopes(token.getScopes());
        basicToken.setFormat(null);
        basicToken.setClaims(mapToClaimsHolder(accessToken.getClaims()));
        basicToken.setMetadata(mapToClaimsHolder(accessToken.getMetadata()));
        return Optional.of(basicToken);
    }

    public static Optional<BasicToken> refreshToken(OAuth2Authorization.Token<OAuth2RefreshToken> refreshToken) {
        if (Objects.isNull(refreshToken)) {
            return Optional.empty();
        }
        OAuth2RefreshToken token = refreshToken.getToken();
        BasicToken basicToken = new BasicToken();
        basicToken.setValue(token.getTokenValue());
        basicToken.setType(null);
        basicToken.setIssuedAt(token.getIssuedAt());
        basicToken.setExpiresAt(token.getExpiresAt());
        basicToken.setInvalidated(refreshToken.isInvalidated());
        basicToken.setScopes(null);
        basicToken.setFormat(null);
        basicToken.setClaims(mapToClaimsHolder(refreshToken.getClaims()));
        basicToken.setMetadata(mapToClaimsHolder(refreshToken.getMetadata()));
        return Optional.of(basicToken);
    }


    public static Optional<BasicToken> oidcIdToken(OAuth2Authorization.Token<OidcIdToken> oidcToken) {
        if (Objects.isNull(oidcToken)) {
            return Optional.empty();
        }
        OidcIdToken token = oidcToken.getToken();
        BasicToken basicToken = new BasicToken();
        basicToken.setValue(token.getTokenValue());
        basicToken.setType(null);
        basicToken.setIssuedAt(token.getIssuedAt());
        basicToken.setExpiresAt(token.getExpiresAt());
        basicToken.setInvalidated(oidcToken.isInvalidated());
        basicToken.setScopes(null);
        basicToken.setFormat(null);
        basicToken.setClaims(mapToClaimsHolder(oidcToken.getClaims()));
        basicToken.setMetadata(mapToClaimsHolder(oidcToken.getMetadata()));
        return Optional.of(basicToken);
    }

    public static Optional<BasicToken> userCode(OAuth2Authorization.Token<OAuth2UserCode> userCode) {
        if (Objects.isNull(userCode)) {
            return Optional.empty();
        }
        OAuth2UserCode token = userCode.getToken();
        BasicToken basicToken = new BasicToken();
        basicToken.setValue(token.getTokenValue());
        basicToken.setType(null);
        basicToken.setIssuedAt(token.getIssuedAt());
        basicToken.setExpiresAt(token.getExpiresAt());
        basicToken.setInvalidated(userCode.isInvalidated());
        basicToken.setScopes(null);
        basicToken.setFormat(null);
        basicToken.setClaims(mapToClaimsHolder(userCode.getClaims()));
        basicToken.setMetadata(mapToClaimsHolder(userCode.getMetadata()));
        return Optional.of(basicToken);
    }

    public static Optional<BasicToken> deviceCode(OAuth2Authorization.Token<OAuth2DeviceCode> deviceCodeToken) {
        if (Objects.isNull(deviceCodeToken)) {
            return Optional.empty();
        }
        OAuth2DeviceCode token = deviceCodeToken.getToken();
        BasicToken basicToken = new BasicToken();
        basicToken.setValue(token.getTokenValue());
        basicToken.setType(null);
        basicToken.setIssuedAt(token.getIssuedAt());
        basicToken.setExpiresAt(token.getExpiresAt());
        basicToken.setInvalidated(deviceCodeToken.isInvalidated());
        basicToken.setScopes(null);
        basicToken.setFormat(null);
        basicToken.setClaims(mapToClaimsHolder(deviceCodeToken.getClaims()));
        basicToken.setMetadata(mapToClaimsHolder(deviceCodeToken.getMetadata()));
        return Optional.of(basicToken);
    }

    public static ClaimsHolder mapToClaimsHolder(Map<String, Object> map) {
        if (CollectionUtils.isEmpty(map)) {
            return new ClaimsHolder(Collections.emptyMap());
        }
        return new ClaimsHolder(map);
    }

    public static Map<String, Object> claimsHolderToMap(ClaimsHolder claimsHolder) {
        if (Objects.isNull(claimsHolder)) {
            return Collections.emptyMap();
        }
        return claimsHolder.claims();
    }

    public static Consumer<Map<String, Object>> claimsHolderConsumer(ClaimsHolder claimsHolder) {
        return map -> {
            if (Objects.nonNull(claimsHolder) && !CollectionUtils.isEmpty(claimsHolder.claims())) {
                map.putAll(claimsHolder.claims());
            }
        };
    }

}
