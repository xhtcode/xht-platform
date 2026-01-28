package com.xht.auth.security.oatuh2.server.authorization;

import com.xht.auth.captcha.exception.CaptchaException;
import com.xht.framework.security.core.userdetails.BasicUserDetails;
import com.xht.framework.security.domain.RequestUserBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.ObjectUtils;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 描述 ：抽象认证处理器
 *
 * @author xht
 **/
@Slf4j
public abstract class AbstractAuthenticationProvider implements AuthenticationProvider {

    protected static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";

    protected static final OAuth2TokenType ID_TOKEN_TOKEN_TYPE = new OAuth2TokenType(OidcParameterNames.ID_TOKEN);

    protected final OAuth2AuthorizationService authorizationService;

    protected final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;

    public AbstractAuthenticationProvider(OAuth2AuthorizationService authorizationService, OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
        this.authorizationService = authorizationService;
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public final Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AbstractAuthenticationToken authenticationToken = (AbstractAuthenticationToken) authentication;
        RequestUserBO requestUserBO = RequestUserBO.builderUser(authenticationToken.getAdditionalParameters());
        UsernamePasswordAuthenticationToken principal;
        try {
            BasicUserDetails userDetails = getAuthenticatedPrincipal(requestUserBO, authentication);
            principal = UsernamePasswordAuthenticationToken.authenticated(userDetails, userDetails.getUsername(), userDetails.getAuthorities());
            principal.setDetails(authentication.getDetails());
            if (!principal.isAuthenticated()) {
                throw new OAuth2AuthenticationException("用户名或密码错误");
            }
            OAuth2ClientAuthenticationToken clientPrincipal = getAuthenticatedClientElseThrowInvalidClient(authenticationToken);
            RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();
            if (Objects.isNull(registeredClient)) {
                throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_CLIENT);
            }
            if (!registeredClient.getAuthorizationGrantTypes().contains(authenticationToken.getGrantType())) {
                throw new OAuth2AuthenticationException(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT);
            }
            // 验证scope
            Set<String> authorizedScopes = getAuthorizedScopes(registeredClient, authenticationToken.getScopes());
            // @formatter:off
        OAuth2Authorization.Builder authorizationBuilder = OAuth2Authorization.withRegisteredClient(registeredClient)
                .authorizedScopes(authorizedScopes)
                .principalName(principal.getName())
                .authorizationGrantType(getGrantType())
                .attribute(Principal.class.getName(), principal.getPrincipal())
                ;
        DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder()
                .registeredClient(registeredClient)
                .principal(principal)
                .authorizationServerContext(AuthorizationServerContextHolder.getContext())
                .authorizedScopes(authorizedScopes)
                .authorizationGrantType(getGrantType())
                .authorizationGrant(clientPrincipal);
        // @formatter:on
            OAuth2AccessToken accessToken = generateAccessToken(tokenContextBuilder, authorizationBuilder);
            OAuth2RefreshToken refreshToken = generateOAuth2RefreshToken(tokenContextBuilder, authorizationBuilder, tokenGenerator, clientPrincipal, registeredClient);
            OidcIdToken oidcIdToken = null;
            if (authorizedScopes.contains(OidcScopes.OPENID)) {
                oidcIdToken = generateOidcIdToken(tokenContextBuilder, authorizationBuilder, tokenGenerator);
            }
            Map<String, Object> additionalParameters = new HashMap<>(1);
            if (Objects.nonNull(oidcIdToken)) {
                additionalParameters.put(OidcParameterNames.ID_TOKEN, oidcIdToken.getTokenValue());
            }
            // Save the OAuth2Authorization
            authorizationBuilder.id(principal.getName());
            OAuth2Authorization authorization = authorizationBuilder.build();
            this.authorizationService.save(authorization);
            return new OAuth2AccessTokenAuthenticationToken(registeredClient, clientPrincipal, accessToken, refreshToken, additionalParameters);
        } catch (CaptchaException e) {
            OAuth2Error oAuth2Error = new OAuth2Error(e.getMessage());
            throw new OAuth2AuthenticationException(oAuth2Error, e);
        } catch (Exception e) {
            OAuth2Error oAuth2Error = new OAuth2Error("用户名或密码错误", e.getMessage(), ERROR_URI);
            throw new OAuth2AuthenticationException(oAuth2Error, e);
        }
    }

    /**
     * 获取已认证的客户端
     *
     * @param authentication 认证信息
     * @return 已认证的客户端
     */
    protected final OAuth2ClientAuthenticationToken getAuthenticatedClientElseThrowInvalidClient(Authentication authentication) {
        OAuth2ClientAuthenticationToken clientPrincipal = null;
        if (OAuth2ClientAuthenticationToken.class.isAssignableFrom(authentication.getPrincipal().getClass())) {
            clientPrincipal = (OAuth2ClientAuthenticationToken) authentication.getPrincipal();
        }
        if (clientPrincipal != null && clientPrincipal.isAuthenticated()) {
            return clientPrincipal;
        }
        throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_CLIENT);
    }

    /**
     * 获取认证过的scope
     *
     * @param registeredClient 客户端
     * @param requestedScopes  请求中的scope
     * @return 认证过的scope
     */
    protected final Set<String> getAuthorizedScopes(RegisteredClient registeredClient, Set<String> requestedScopes) {
        // Default to configured scopes
        Set<String> authorizedScopes = registeredClient.getScopes();
        if (!ObjectUtils.isEmpty(requestedScopes)) {
            Set<String> unauthorizedScopes = requestedScopes.stream()
                    .filter(requestedScope -> !registeredClient.getScopes().contains(requestedScope))
                    .collect(Collectors.toSet());
            if (!ObjectUtils.isEmpty(unauthorizedScopes)) {
                throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_SCOPE);
            }
            authorizedScopes = new LinkedHashSet<>(requestedScopes);
        }

        if (log.isTraceEnabled()) {
            log.trace("Validated token request parameters");
        }
        return authorizedScopes;
    }

    /**
     * 生成 Access token
     *
     * @param tokenContextBuilder  oauth2token 构建
     * @param authorizationBuilder oauth2 认证构建 {@link OAuth2Authorization.Builder}
     * @return {@link OAuth2AccessToken}
     */
    protected final OAuth2AccessToken generateAccessToken(DefaultOAuth2TokenContext.Builder tokenContextBuilder, OAuth2Authorization.Builder authorizationBuilder) {
        // ----- Access token -----
        OAuth2TokenContext tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.ACCESS_TOKEN).build();
        OAuth2Token generatedAccessToken = tokenGenerator.generate(tokenContext);
        if (generatedAccessToken == null) {
            OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                    "The token generator failed to generate the access token.", ERROR_URI);
            throw new OAuth2AuthenticationException(error);
        }
        OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
                generatedAccessToken.getTokenValue(), generatedAccessToken.getIssuedAt(),
                generatedAccessToken.getExpiresAt(), tokenContext.getAuthorizedScopes());
        if (generatedAccessToken instanceof ClaimAccessor) {
            authorizationBuilder
                    .token(accessToken, (metadata) ->
                            metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME, ((ClaimAccessor) generatedAccessToken).getClaims()
                            ));
        } else {
            authorizationBuilder.accessToken(accessToken);
        }
        return accessToken;
    }

    /**
     * 生成 Refresh token
     *
     * @param tokenContextBuilder  oauth2token 构建
     * @param authorizationBuilder oauth2 认证构建 {@link OAuth2Authorization.Builder}
     * @param tokenGenerator       token 生成策略{@link OAuth2TokenGenerator}
     * @param clientPrincipal      {@link OAuth2ClientAuthenticationToken}
     * @param registeredClient     {@link RegisteredClient}
     * @return {@link OAuth2RefreshToken}
     */
    protected final OAuth2RefreshToken generateOAuth2RefreshToken(DefaultOAuth2TokenContext.Builder tokenContextBuilder, OAuth2Authorization.Builder authorizationBuilder, OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator,
                                                                  OAuth2ClientAuthenticationToken clientPrincipal,
                                                                  RegisteredClient registeredClient) {
        // ----- Refresh token -----
        OAuth2RefreshToken refreshToken = null;
        if (registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.REFRESH_TOKEN) &&
                // Do not issue refresh token to public client
                !clientPrincipal.getClientAuthenticationMethod().equals(ClientAuthenticationMethod.NONE)) {
            OAuth2TokenContext tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.REFRESH_TOKEN).build();
            OAuth2Token generatedRefreshToken = tokenGenerator.generate(tokenContext);
            if (!(generatedRefreshToken instanceof OAuth2RefreshToken)) {
                OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                        "The token generator failed to generate the refresh token.", ERROR_URI);
                throw new OAuth2AuthenticationException(error);
            }
            refreshToken = (OAuth2RefreshToken) generatedRefreshToken;
            authorizationBuilder.refreshToken(refreshToken);
        }
        return refreshToken;
    }


    /**
     * 创建oidc Token
     *
     * @param tokenContextBuilder  oauth2token 构建
     * @param authorizationBuilder oauth2 认证构建 {@link OAuth2Authorization.Builder}
     * @param tokenGenerator       token 生成策略{@link OAuth2TokenGenerator}
     * @return {@link OidcIdToken}
     */
    protected final OidcIdToken generateOidcIdToken(DefaultOAuth2TokenContext.Builder tokenContextBuilder,
                                                    OAuth2Authorization.Builder authorizationBuilder,
                                                    OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
        DefaultOAuth2TokenContext tokenContext = tokenContextBuilder
                .tokenType(ID_TOKEN_TOKEN_TYPE)
                // ID令牌定制器可能需要访问访问令牌、刷新令牌
                .authorization(authorizationBuilder.build())
                .build();
        OAuth2Token generatedIdToken =
                Optional.ofNullable(tokenGenerator.generate(tokenContext))
                        .orElseThrow(() -> new OAuth2AuthenticationException(new OAuth2Error("getCode", "desc", "uri")));
        if (!(generatedIdToken instanceof Jwt)) {
            OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                    "The token generator failed to generate the ID token.", ERROR_URI);
            throw new OAuth2AuthenticationException(error);
        }
        // 生成id_token
        OidcIdToken idToken =
                new OidcIdToken(generatedIdToken.getTokenValue(), generatedIdToken.getIssuedAt(),
                        generatedIdToken.getExpiresAt(), ((Jwt) generatedIdToken).getClaims());
        authorizationBuilder.token(idToken, (metadata) -> metadata
                .put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME, idToken.getClaims()));
        return idToken;
    }

    /**
     * 获取认证过的用户信息
     *
     * @param requestUserBO  用户请求信息
     * @param authentication 认证信息
     * @return 认证信息
     */
    protected abstract BasicUserDetails getAuthenticatedPrincipal(final RequestUserBO requestUserBO, final Authentication authentication);

    /**
     * 获取认证类型.
     *
     * @return 认证类型
     */
    protected abstract AuthorizationGrantType getGrantType();

}
