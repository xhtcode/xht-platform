package com.xht.auth.security.oatuh2.server.authorization.client;

import com.xht.modules.admin.oauth2.domain.response.SysOauth2ClientResponse;
import com.xht.modules.admin.oauth2.enums.Oauth2ClientAutoApproveEnums;
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
class OAuth2RegisteredClientFunction implements Function<SysOauth2ClientResponse, RegisteredClient> {

    /**
     * 根据SysOauth2ClientResponse对象构建RegisteredClient对象
     *
     * @param clientResponse SysOauth2ClientResponse对象，包含OAuth2客户端信息
     * @return RegisteredClient对象，表示已注册的OAuth2客户端
     */
    @Override
    public RegisteredClient apply(SysOauth2ClientResponse clientResponse) {
        RegisteredClient.Builder registeredClientBuilder = RegisteredClient.withId(String.valueOf(clientResponse.getClientId()))
                .clientId(clientResponse.getClientId()).clientSecret(clientResponse.getClientSecret())
                .clientIdIssuedAt(convertToInstant(clientResponse.getClientIdIssuedAt()))
                .clientSecretExpiresAt(convertToInstant(clientResponse.getClientSecretExpiresAt()))
                .clientName(clientResponse.getClientName())
                .clientAuthenticationMethods(item -> {
                    Set<String> clientAuthenticationMethods = clientResponse.getClientAuthenticationMethods();
                    for (String clientAuthenticationMethod : clientAuthenticationMethods) {
                        item.add(resolveClientAuthenticationMethod(clientAuthenticationMethod));
                    }
                })
                .scopes(item -> item.addAll(clientResponse.getScopes()))
                .redirectUris(item -> item.addAll(clientResponse.getRedirectUris()))
                .authorizationGrantTypes((authorizationGrantTypes) -> authorizationGrantTypes
                        .addAll(formatAuthorizationGrantTypes(clientResponse.getAuthorizationGrantTypes())))
                .clientSettings(ClientSettings.builder()
                        .requireAuthorizationConsent(Objects.equals(clientResponse.getAutoApprove(), Oauth2ClientAutoApproveEnums.YES))
                        .build())
                .tokenSettings(tokenSettings(clientResponse));
        return registeredClientBuilder.build();
    }

    /**
     * 将LocalDateTime转换为Instant对象
     *
     * @param localDateTime 需要转换的LocalDateTime对象，可以为null
     * @return 转换后的Instant对象，如果输入为null则返回null
     */
    private Instant convertToInstant(LocalDateTime localDateTime) {
        if (Objects.isNull(localDateTime)) {
            return null;
        }
        // 将LocalDateTime转换为系统默认时区的Instant对象
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant();
    }


    /**
     * 根据OAuth2客户端响应配置构建令牌设置
     *
     * @param clientResponse OAuth2客户端响应对象，包含访问令牌和刷新令牌的有效期信息
     * @return TokenSettings 令牌配置对象，包含令牌格式、有效期等设置
     */
    private TokenSettings tokenSettings(SysOauth2ClientResponse clientResponse) {
        return TokenSettings.builder()
                .accessTokenFormat(OAuth2TokenFormat.REFERENCE)
                .accessTokenTimeToLive(Duration.ofSeconds(clientResponse.getAccessTokenValidity()))
                .refreshTokenTimeToLive(Duration.ofSeconds(clientResponse.getRefreshTokenValidity()))
                .build();
    }


    /**
     * 将授权授予类型字符串集合转换为AuthorizationGrantType对象集合
     *
     * @param authorizationGrantTypes 包含授权授予类型字符串的集合，可能包含null值
     * @return 转换后的AuthorizationGrantType对象集合，不包含null值
     */
    private Set<AuthorizationGrantType> formatAuthorizationGrantTypes(Set<String> authorizationGrantTypes) {
        // 过滤掉空值并转换为AuthorizationGrantType对象
        return authorizationGrantTypes.stream().filter(Objects::nonNull).map(AuthorizationGrantType::new).collect(Collectors.toSet());
    }


    /**
     * 根据客户端认证方法字符串解析并返回对应的ClientAuthenticationMethod对象
     *
     * @param clientAuthenticationMethod 客户端认证方法的字符串表示
     * @return 对应的ClientAuthenticationMethod枚举对象或自定义认证方法对象
     */
    private static ClientAuthenticationMethod resolveClientAuthenticationMethod(String clientAuthenticationMethod) {
        if (ClientAuthenticationMethod.CLIENT_SECRET_BASIC.getValue().equals(clientAuthenticationMethod)) {
            return ClientAuthenticationMethod.CLIENT_SECRET_BASIC;
        } else if (ClientAuthenticationMethod.CLIENT_SECRET_POST.getValue().equals(clientAuthenticationMethod)) {
            return ClientAuthenticationMethod.CLIENT_SECRET_POST;
        } else if (ClientAuthenticationMethod.NONE.getValue().equals(clientAuthenticationMethod)) {
            return ClientAuthenticationMethod.NONE;
        }
        // 自定义客户端认证方法处理
        return new ClientAuthenticationMethod(clientAuthenticationMethod);
    }

}