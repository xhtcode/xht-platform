package com.xht.auth.configuration;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.xht.auth.captcha.service.ICaptchaService;
import com.xht.auth.security.oatuh2.server.authorization.password.PassWordAuthenticationConverter;
import com.xht.auth.security.oatuh2.server.authorization.password.PassWordAuthenticationProvider;
import com.xht.auth.security.oatuh2.server.authorization.phone.PhoneAuthenticationConverter;
import com.xht.auth.security.oatuh2.server.authorization.phone.PhoneAuthenticationProvider;
import com.xht.auth.security.oatuh2.server.authorization.token.OpaqueTokenClaimsCustomizer;
import com.xht.auth.security.oatuh2.server.authorization.token.XhtOAuth2AccessTokenGenerator;
import com.xht.auth.security.oatuh2.server.authorization.token.XhtOAuth2RefreshTokenGenerator;
import com.xht.auth.security.web.authentication.*;
import com.xht.framework.oauth2.handler.ResourceAuthenticationEntryPoint;
import com.xht.framework.oauth2.handler.ResourceBearerTokenResolver;
import com.xht.framework.security.configurers.CustomAuthorizeHttpRequestsConfigurer;
import com.xht.framework.security.core.userdetails.BasicUserDetailsService;
import com.xht.framework.security.properties.PermitAllUrlProperties;
import com.xht.framework.security.web.access.Http401AccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.DelegatingOAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.JwtGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

/**
 * 授权服务器配置
 *
 * @author xht
 **/
@Slf4j
@Configuration
@RequiredArgsConstructor
public class AuthorizationServerAutoConfiguration {

    private final BasicUserDetailsService basicUserDetailsService;

    private final ICaptchaService iCaptchaService;

    private final PermitAllUrlProperties permitAllUrlProperties;

    private final ResourceAuthenticationEntryPoint resourceAuthenticationEntryPoint;

    private final ResourceBearerTokenResolver resourceBearerTokenResolver;

    private final OpaqueTokenIntrospector opaqueTokenIntrospector;

    @Bean
    @Order(1)
    // @formatter:off
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http,
                                                                      OAuth2AuthorizationService authorizationService,
                                                                      OAuth2TokenGenerator<?> tokenGenerator

    ) throws Exception {
        PassWordAuthenticationProvider passWordAuthenticationProvider = new PassWordAuthenticationProvider(authorizationService, tokenGenerator, basicUserDetailsService, iCaptchaService);
        PassWordAuthenticationConverter passWordAuthenticationConverter = new PassWordAuthenticationConverter();
        PhoneAuthenticationProvider phoneAuthenticationProvider = new PhoneAuthenticationProvider(authorizationService, tokenGenerator, basicUserDetailsService, iCaptchaService);
        PhoneAuthenticationConverter phoneAuthenticationConverter = new PhoneAuthenticationConverter();
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = OAuth2AuthorizationServerConfigurer.authorizationServer();
        authorizationServerConfigurer.clientAuthentication(configurer-> configurer.errorResponseHandler(new OAuth2ClientAuthenticationFailureHandler()));
        http.securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .with(authorizationServerConfigurer, (authorizationServer) ->
                        authorizationServer
                                .oidc(Customizer.withDefaults())  // Enable OpenID Connect 1.0
                                .tokenEndpoint(tokenEndpoint -> {
                                    tokenEndpoint.accessTokenResponseHandler(new TokenAuthenticationSuccessHandler());
                                    tokenEndpoint.errorResponseHandler(new TokenAuthenticationFailureHandler());
                                    tokenEndpoint.authenticationProviders(providers -> {
                                        providers.add(passWordAuthenticationProvider);
                                        providers.add(phoneAuthenticationProvider);
                                    });
                                    tokenEndpoint.accessTokenRequestConverters((converters) -> {
                                        converters.add(passWordAuthenticationConverter);
                                        converters.add(phoneAuthenticationConverter);
                                    });
                                })
                                .tokenRevocationEndpoint(tokenEndpoint ->{
                                    tokenEndpoint.revocationResponseHandler(new TokenRevocationAuthenticationSuccessHandler());
                                    tokenEndpoint.errorResponseHandler(new TokenRevocationAuthenticationFailureHandler());
                                })
                )
                .authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated())
                .exceptionHandling(
                        (exceptions) ->
                        {
                            LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint = new LoginUrlAuthenticationEntryPoint("/login");
                            MediaTypeRequestMatcher mediaTypeRequestMatcher = new MediaTypeRequestMatcher(MediaType.TEXT_HTML);
                            exceptions.defaultAuthenticationEntryPointFor(loginUrlAuthenticationEntryPoint, mediaTypeRequestMatcher);
                        }
                );
        return http.build();
    }
    // @formatter:on
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        CustomAuthorizeHttpRequestsConfigurer requestsConfigurer = new CustomAuthorizeHttpRequestsConfigurer(permitAllUrlProperties);
        // 开启CORS配置，配合下边的CorsConfigurationSource配置实现跨域配置
        http.cors(Customizer.withDefaults());
        // 禁用csrf
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(requestsConfigurer);
        http.formLogin(Customizer.withDefaults());
        http.oauth2ResourceServer(configurer -> {
            configurer.opaqueToken(opaqueToken -> opaqueToken.introspector(opaqueTokenIntrospector));
            configurer.authenticationEntryPoint(resourceAuthenticationEntryPoint);
            configurer.bearerTokenResolver(resourceBearerTokenResolver);
        });
        http.exceptionHandling(handlingConfigurer -> {
            handlingConfigurer.accessDeniedHandler(new Http401AccessDeniedHandler());// 请求未授权的接口
        });
        return http.build();
    }
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
                .issuer("http://127.0.0.1:9000")
                .build();
    }

    @Bean
    public OAuth2TokenGenerator<?> tokenGenerator(JWKSource<SecurityContext> jwkSource) {
        JwtGenerator jwtGenerator = new JwtGenerator(new NimbusJwtEncoder(jwkSource));
        XhtOAuth2AccessTokenGenerator accessTokenGenerator = new XhtOAuth2AccessTokenGenerator();
        accessTokenGenerator.setAccessTokenCustomizer(new OpaqueTokenClaimsCustomizer());
        XhtOAuth2RefreshTokenGenerator refreshTokenGenerator = new XhtOAuth2RefreshTokenGenerator();
        return new DelegatingOAuth2TokenGenerator(jwtGenerator, accessTokenGenerator, refreshTokenGenerator);
    }


    private KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }


    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    /**
     * jwt编码器.
     *
     * @param jwkSource jwk来源
     * @return jwt编码器
     */
    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

}
