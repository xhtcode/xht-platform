package com.xht.framework.oauth2.configurers;

import cn.hutool.core.util.IdUtil;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.xht.framework.oauth2.handler.ResourceAuthenticationEntryPoint;
import com.xht.framework.oauth2.handler.ResourceBearerTokenResolver;
import com.xht.framework.oauth2.introspection.ResourceOpaqueTokenIntrospector;
import com.xht.framework.oauth2.utils.JwtUtils;
import com.xht.framework.security.configurers.CustomAuthorizeHttpRequestsConfigurer;
import com.xht.framework.security.properties.PermitAllUrlProperties;
import com.xht.framework.security.web.Http401UnauthorizedEntryPoint;
import com.xht.framework.security.web.access.Http401AccessDeniedHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import static com.xht.framework.security.constant.SecurityConstant.RESOURCE_SERVER_BEAN_NAME;

/**
 * 资源服务器配置
 *
 * @author xht
 **/
@Slf4j
@Configuration(value = RESOURCE_SERVER_BEAN_NAME)
@EnableWebSecurity
@EnableMethodSecurity
public class ResourceServerConfiguration {


    private final PermitAllUrlProperties permitAllUrlProperties;

    private final ResourceAuthenticationEntryPoint resourceAuthenticationEntryPoint;

    private final ResourceBearerTokenResolver resourceBearerTokenResolver;

    private final ResourceOpaqueTokenIntrospector resourceOpaqueTokenIntrospector;

    public ResourceServerConfiguration(PermitAllUrlProperties permitAllUrlProperties,
                                       ResourceAuthenticationEntryPoint resourceAuthenticationEntryPoint,
                                       ResourceBearerTokenResolver resourceBearerTokenResolver,
                                       ResourceOpaqueTokenIntrospector resourceOpaqueTokenIntrospector
    ) {
        this.permitAllUrlProperties = permitAllUrlProperties;
        this.resourceAuthenticationEntryPoint = resourceAuthenticationEntryPoint;
        this.resourceBearerTokenResolver = resourceBearerTokenResolver;
        this.resourceOpaqueTokenIntrospector = resourceOpaqueTokenIntrospector;
        log.info(">>>>>>资源服务器配置 初始化 ... <<<<<<");
    }

    /**
     * 资源服务器配置
     *
     * @param http HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception 异常
     */
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        CustomAuthorizeHttpRequestsConfigurer requestsConfigurer = new CustomAuthorizeHttpRequestsConfigurer(permitAllUrlProperties);
        // @formatter:off
        http
                .authorizeHttpRequests(requestsConfigurer)
                .csrf(AbstractHttpConfigurer::disable)
                .oauth2ResourceServer(configurer -> {
                    configurer.opaqueToken((token) -> token.introspector(resourceOpaqueTokenIntrospector));
                    configurer.authenticationEntryPoint(resourceAuthenticationEntryPoint);
                    configurer.bearerTokenResolver(resourceBearerTokenResolver);
                })
                .exceptionHandling(handlingConfigurer -> {
                    handlingConfigurer.authenticationEntryPoint(new Http401UnauthorizedEntryPoint());//请求未认证的接口
                    handlingConfigurer.accessDeniedHandler(new Http401AccessDeniedHandler());// 请求未授权的接口
                })
        ;
        // @formatter:on
        return http.build();
    }


    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        KeyPair keyPair = JwtUtils.generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(IdUtil.nanoId())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }


    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }
}
