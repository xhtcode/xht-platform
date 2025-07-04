package com.xht.boot.oauth2.function;

import cn.hutool.core.util.BooleanUtil;
import com.xht.boot.oauth2.domain.dto.OAuth2RegisteredClientDTO;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;
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

    @Override
    public RegisteredClient apply(OAuth2RegisteredClientDTO clientDTO) {
        RegisteredClient.Builder registeredClientBuilder = RegisteredClient.withId(clientDTO.getId())
                .clientId(clientDTO.getClientId())
                .clientIdIssuedAt(clientDTO.getClientIdIssuedAt())
                .clientSecret(clientDTO.getClientSecret())
                .clientSecretExpiresAt(clientDTO.getClientSecretExpiresAt())
                .clientName(clientDTO.getClientName())
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantTypes((authorizationGrantTypes) -> authorizationGrantTypes
                        .addAll(formatAuthorizationGrantTypes(clientDTO.getAuthorizationGrantTypes())))
                .clientSettings(ClientSettings.builder()
                        .requireAuthorizationConsent(!BooleanUtil.toBoolean(clientDTO.getAutoApprove()))
                        .build())
                .tokenSettings(tokenSettings(clientDTO));
        return registeredClientBuilder.build();
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