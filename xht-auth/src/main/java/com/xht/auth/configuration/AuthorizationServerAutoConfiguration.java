package com.xht.auth.configuration;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.xht.auth.captcha.service.ICaptchaService;
import com.xht.auth.configuration.properties.XhtOauth2Properties;
import com.xht.auth.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoMapper;
import com.xht.auth.security.oauth2.server.authorization.password.PassWordAuthenticationConverter;
import com.xht.auth.security.oauth2.server.authorization.password.PassWordAuthenticationProvider;
import com.xht.auth.security.oauth2.server.authorization.phone.PhoneAuthenticationConverter;
import com.xht.auth.security.oauth2.server.authorization.phone.PhoneAuthenticationProvider;
import com.xht.auth.security.oauth2.server.authorization.token.JwtTokenCustomizer;
import com.xht.auth.security.oauth2.server.authorization.web.AuthorizationEndpointFailureHandler;
import com.xht.auth.security.oauth2.server.authorization.web.AuthorizationEndpointSuccessHandler;
import com.xht.auth.security.web.authentication.*;
import com.xht.framework.core.domain.R;
import com.xht.framework.core.utils.ServletUtil;
import com.xht.framework.security.core.userdetails.BasicUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.DelegatingOAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.JwtGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2RefreshTokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
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

    private final XhtOauth2Properties xhtOauth2Properties;



    @Bean
    @Order(1)
    // @formatter:off
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http,
                                                                      OAuth2AuthorizationService authorizationService,
                                                                      OAuth2TokenGenerator<?> tokenGenerator, JdbcTemplate jdbcTemplate

    ) throws Exception {
        XhtOauth2Properties.AuthorizationServer authorizationServerProperties = xhtOauth2Properties.getAuthorizationServer();
        PassWordAuthenticationProvider passWordAuthenticationProvider = new PassWordAuthenticationProvider(authorizationService, tokenGenerator, basicUserDetailsService, iCaptchaService);
        PassWordAuthenticationConverter passWordAuthenticationConverter = new PassWordAuthenticationConverter();
        PhoneAuthenticationProvider phoneAuthenticationProvider = new PhoneAuthenticationProvider(authorizationService, tokenGenerator, basicUserDetailsService, iCaptchaService);
        PhoneAuthenticationConverter phoneAuthenticationConverter = new PhoneAuthenticationConverter();
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
        authorizationServerConfigurer.clientAuthentication(configurer-> configurer.errorResponseHandler(new OAuth2ClientAuthenticationFailureHandler()));
        http.securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .with(authorizationServerConfigurer, (authorizationServer) ->
                        authorizationServer
                                .oidc(oidc->{
                                    oidc.userInfoEndpoint(userInfoEndpoint-> {
                                        userInfoEndpoint.errorResponseHandler((request,response,exception)-> {
                                                log.error("用户信息获取失败",exception);
                                                ServletUtil.writeJson(response, R.error().msg(exception.getMessage()).build());
                                            });
                                        userInfoEndpoint.userInfoMapper(new OidcUserInfoMapper());
                                    });
                                })  // Enable OpenID Connect 1.0
                                .authorizationEndpoint(authorizationEndpoint -> {
                                    authorizationEndpoint.consentPage(authorizationServerProperties.getConsentPage());
                                    authorizationEndpoint.authorizationResponseHandler(new AuthorizationEndpointSuccessHandler());
                                    authorizationEndpoint.errorResponseHandler(new AuthorizationEndpointFailureHandler());
                                })
                                // 令牌端点
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
                                // 令牌注销端点
                                .tokenRevocationEndpoint(tokenEndpoint ->{
                                    tokenEndpoint.revocationResponseHandler(new TokenRevocationAuthenticationSuccessHandler());
                                    tokenEndpoint.errorResponseHandler(new TokenRevocationAuthenticationFailureHandler());
                                })
                )
                .exceptionHandling((exceptions) ->            {
                    LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint = new LoginUrlAuthenticationEntryPoint(xhtOauth2Properties.getAuthorizationServer().getLoginPage());
                    MediaTypeRequestMatcher mediaTypeRequestMatcher = new MediaTypeRequestMatcher(MediaType.TEXT_HTML);
                    exceptions.defaultAuthenticationEntryPointFor(loginUrlAuthenticationEntryPoint, mediaTypeRequestMatcher);
                })
                .authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated());
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setJdbcTemplate(jdbcTemplate);
        http.rememberMe(rememberMeConfigurer -> {
            rememberMeConfigurer.rememberMeParameter("rememberMe");
            rememberMeConfigurer.rememberMeCookieName("xht-token");
            rememberMeConfigurer.tokenValiditySeconds(6000);
            rememberMeConfigurer.userDetailsService(basicUserDetailsService);
            rememberMeConfigurer.tokenRepository(jdbcTokenRepository);
        });
        return http.build();
    }
    // @formatter:on

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
                .issuer(xhtOauth2Properties.getIssuer())
                .build();
    }

    @Bean
    public OAuth2TokenGenerator<?> tokenGenerator(JWKSource<SecurityContext> jwkSource) {
        JwtGenerator jwtGenerator = new JwtGenerator(new NimbusJwtEncoder(jwkSource));
        jwtGenerator.setJwtCustomizer(new JwtTokenCustomizer());
        OAuth2RefreshTokenGenerator refreshTokenGenerator = new OAuth2RefreshTokenGenerator();
        return new DelegatingOAuth2TokenGenerator(jwtGenerator, refreshTokenGenerator);
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
