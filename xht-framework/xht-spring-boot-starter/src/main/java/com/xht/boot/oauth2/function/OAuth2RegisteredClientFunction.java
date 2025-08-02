package com.xht.boot.oauth2.function;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.BooleanUtil;
import com.xht.boot.oauth2.domain.dto.OAuth2RegisteredClientDTO;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * OAuth2RegisteredClientFunction
 *
 * @author xht
 */
public class OAuth2RegisteredClientFunction implements Function<OAuth2RegisteredClientDTO, RegisteredClient> {
    private static final DateTimeFormatter fmt

            = DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_MS_PATTERN).withLocale(Locale.getDefault()).withZone(ZoneId.systemDefault());

    @Override
    public RegisteredClient apply(OAuth2RegisteredClientDTO clientDTO) {
        RegisteredClient.Builder registeredClientBuilder = RegisteredClient.withId(String.valueOf(clientDTO.getClientId()))
                .clientId(clientDTO.getClientId()).clientSecret(clientDTO.getClientSecret())
                .clientIdIssuedAt(convertToInstant(clientDTO.getClientIdIssuedAt()))
                .clientSecretExpiresAt(convertToInstant(clientDTO.getClientSecretExpiresAt()))
                .clientName(clientDTO.getClientName())
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .scopes(item -> item.addAll(clientDTO.getScopes()))
                .redirectUris(item -> item.addAll(clientDTO.getRedirectUris()))
                .authorizationGrantTypes((authorizationGrantTypes) -> authorizationGrantTypes
                        .addAll(formatAuthorizationGrantTypes(clientDTO.getAuthorizationGrantTypes())))
                .clientSettings(ClientSettings.builder()
                        .requireAuthorizationConsent(!BooleanUtil.toBoolean(clientDTO.getAutoApprove()))
                        .build())
                .tokenSettings(tokenSettings(clientDTO));
        return registeredClientBuilder.build();
    }

    private Instant convertToInstant(LocalDateTime localDateTime) {
        if (Objects.isNull(localDateTime)) {
            return null;
        }
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant();
    }
    private TokenSettings tokenSettings(OAuth2RegisteredClientDTO clientDTO) {
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