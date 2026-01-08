package com.xht.auth.security.oatuh2.server.authorization.client;

import cn.hutool.core.util.BooleanUtil;
import com.xht.api.system.domain.response.SysOauth2ClientResponse;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * OAuth2RegisteredClientFunction
 *
 * @author xht
 */
public class OAuth2RegisteredClientFunction implements Function<SysOauth2ClientResponse, RegisteredClient> {
    @Override
    public RegisteredClient apply(SysOauth2ClientResponse clientResponse) {
        RegisteredClient.Builder registeredClientBuilder = RegisteredClient.withId(String.valueOf(clientResponse.getClientId()))
                .clientId(clientResponse.getClientId()).clientSecret(clientResponse.getClientSecret())
                .clientIdIssuedAt(convertToInstant(clientResponse.getClientIdIssuedAt()))
                .clientSecretExpiresAt(convertToInstant(clientResponse.getClientSecretExpiresAt()))
                .clientName(clientResponse.getClientName())
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .scopes(item -> item.addAll(clientResponse.getScopes()))
                .redirectUris(item -> item.addAll(clientResponse.getRedirectUris()))
                .authorizationGrantTypes((authorizationGrantTypes) -> authorizationGrantTypes
                        .addAll(formatAuthorizationGrantTypes(clientResponse.getAuthorizationGrantTypes())))
                .clientSettings(ClientSettings.builder()
                        .requireAuthorizationConsent(!BooleanUtil.toBoolean(clientResponse.getAutoApprove()))
                        .build())
                .tokenSettings(tokenSettings(clientResponse));
        return registeredClientBuilder.build();
    }

    private Instant convertToInstant(LocalDateTime localDateTime) {
        if (Objects.isNull(localDateTime)) {
            return null;
        }
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant();
    }

    private TokenSettings tokenSettings(SysOauth2ClientResponse clientDTO) {
        return TokenSettings.builder()
                .accessTokenFormat(OAuth2TokenFormat.REFERENCE)
                .accessTokenTimeToLive(Duration.ofSeconds(clientDTO.getAccessTokenValidity()))
                .refreshTokenTimeToLive(Duration.ofSeconds(clientDTO.getRefreshTokenValidity()))
                .build();
    }

    private Set<AuthorizationGrantType> formatAuthorizationGrantTypes(Set<String> authorizationGrantTypes) {
        return authorizationGrantTypes.stream().filter(Objects::nonNull).map(AuthorizationGrantType::new).collect(Collectors.toSet());
    }
}