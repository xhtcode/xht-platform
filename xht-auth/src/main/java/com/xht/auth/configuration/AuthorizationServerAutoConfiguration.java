package com.xht.auth.configuration;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.xht.auth.captcha.service.ICaptchaService;
import com.xht.auth.configuration.properties.XhtOauth2Properties;
import com.xht.auth.security.oauth2.OAuth2AuthorizationServerCustomizer;
import com.xht.auth.security.oauth2.XhtAuthorizationServerConfigurer;
import com.xht.auth.security.oauth2.jwt.XhtNimbusJwtDecoder;
import com.xht.auth.security.oauth2.server.authorization.token.JwtTokenCustomizer;
import com.xht.auth.security.oauth2.server.authorization.token.OpaqueTokenClaimsCustomizer;
import com.xht.auth.security.oauth2.server.authorization.token.XhtOAuth2AccessTokenGenerator;
import com.xht.auth.security.oauth2.server.authorization.token.XhtOAuth2RefreshTokenGenerator;
import com.xht.auth.security.web.authentication.AuthorizationServerLoginUrlAuthenticationEntryPoint;
import com.xht.framework.security.core.userdetails.BasicUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
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
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.web.SecurityFilterChain;
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
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http, OAuth2AuthorizationService authorizationService) throws Exception {
        XhtOauth2Properties.AuthorizationServer authorizationServerProperties = xhtOauth2Properties.getAuthorizationServer();
        // 配置授权服务器
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
        // 配置授权服务器端点
        OAuth2AuthorizationServerCustomizer authorizationServerCustomizer = new OAuth2AuthorizationServerCustomizer();
        authorizationServerCustomizer.setAuthorizationService(authorizationService);
        authorizationServerCustomizer.setAuthorizationServerProperties(authorizationServerProperties);
        http.securityMatcher(authorizationServerConfigurer.getEndpointsMatcher()).with(authorizationServerConfigurer, authorizationServerCustomizer);
        // 配置授权服务器 扩展
        XhtAuthorizationServerConfigurer xhtAuthorizationServerConfigurer = XhtAuthorizationServerConfigurer.authorizationServer(authorizationServerConfigurer);
        http.with(xhtAuthorizationServerConfigurer, (authorizationServer) -> {
            authorizationServer.setCaptchaService(iCaptchaService);
            authorizationServer.setUserDetailsService(basicUserDetailsService);
        });
        // 配置授权服务器 登录页面
        http.exceptionHandling((exceptions) -> {
            AuthorizationServerLoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint = new AuthorizationServerLoginUrlAuthenticationEntryPoint(xhtOauth2Properties.getAuthorizationServer().getLoginPage());
            MediaTypeRequestMatcher mediaTypeRequestMatcher = new MediaTypeRequestMatcher(MediaType.TEXT_HTML);
            exceptions.defaultAuthenticationEntryPointFor(loginUrlAuthenticationEntryPoint, mediaTypeRequestMatcher);
        });
        // 配置授权服务器 认证
        http.authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated());
        http.oauth2ResourceServer(rs -> {
        });
        return http.build();
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
                .issuer(xhtOauth2Properties.getIssuer())
                .build();
    }

    @Bean
    public OAuth2TokenGenerator<?> tokenGenerator(JwtEncoder jwtEncoder) {
        JwtGenerator jwtGenerator = new JwtGenerator(jwtEncoder);
        jwtGenerator.setJwtCustomizer(new JwtTokenCustomizer());
        XhtOAuth2AccessTokenGenerator accessTokenGenerator = new XhtOAuth2AccessTokenGenerator();
        accessTokenGenerator.setAccessTokenCustomizer(new OpaqueTokenClaimsCustomizer());
        XhtOAuth2RefreshTokenGenerator refreshTokenGenerator = new XhtOAuth2RefreshTokenGenerator();
        return new DelegatingOAuth2TokenGenerator(accessTokenGenerator, jwtGenerator, refreshTokenGenerator);
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
        JwtDecoder jwtDecoder = OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
        return new XhtNimbusJwtDecoder(jwtDecoder);
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
