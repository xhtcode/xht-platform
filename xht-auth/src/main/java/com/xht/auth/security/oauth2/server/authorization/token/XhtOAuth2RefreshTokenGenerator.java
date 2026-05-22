package com.xht.auth.security.oauth2.server.authorization.token;

import cn.hutool.core.util.IdUtil;
import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

import java.time.Instant;

/**
 * An {@link OAuth2TokenGenerator} that generates an {@link OAuth2RefreshToken}.
 *
 * @author Joe Grandja
 * @see OAuth2TokenGenerator
 * @see OAuth2RefreshToken
 * @since 0.2.3
 */
public final class XhtOAuth2RefreshTokenGenerator implements OAuth2TokenGenerator<OAuth2RefreshToken> {

    private static boolean isPublicClientForAuthorizationCodeGrant(OAuth2TokenContext context) {
        // @formatter:off
		if (AuthorizationGrantType.AUTHORIZATION_CODE.equals(context.getAuthorizationGrantType()) &&
				(context.getAuthorizationGrant().getPrincipal() instanceof OAuth2ClientAuthenticationToken clientPrincipal)) {
			return clientPrincipal.getClientAuthenticationMethod().equals(ClientAuthenticationMethod.NONE);
		}
		// @formatter:on
        return false;
    }

    @Nullable
    @Override
    public OAuth2RefreshToken generate(OAuth2TokenContext context) {
        if (!OAuth2TokenType.REFRESH_TOKEN.equals(context.getTokenType())) {
            return null;
        }
        if (isPublicClientForAuthorizationCodeGrant(context)) {
            // Do not issue refresh token to public client
            return null;
        }

        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plus(context.getRegisteredClient().getTokenSettings().getRefreshTokenTimeToLive());
        return new OAuth2RefreshToken(IdUtil.fastSimpleUUID(), issuedAt, expiresAt);
    }

}
