package com.xht.framework.oauth2.autoconfigure;

import com.xht.framework.oauth2.handler.ResourceBearerTokenResolver;
import com.xht.framework.security.configurers.CustomAuthorizeHttpRequestsConfigurer;
import com.xht.framework.security.properties.PermitAllUrlProperties;
import com.xht.framework.security.web.XhtAuthenticationEntryPoint;
import com.xht.framework.security.web.access.XhtAccessDeniedHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 资源服务器配置
 *
 * @author xht
 **/
@Slf4j
@EnableWebSecurity
@EnableMethodSecurity
public class ResourceServerAutoConfiguration {

    private final PermitAllUrlProperties permitAllUrlProperties;

    private final ResourceBearerTokenResolver resourceBearerTokenResolver;

    private final OpaqueTokenIntrospector opaqueTokenIntrospector;


    public ResourceServerAutoConfiguration(PermitAllUrlProperties permitAllUrlProperties,
                                           OpaqueTokenIntrospector opaqueTokenIntrospector
    ) {
        this.permitAllUrlProperties = permitAllUrlProperties;
        this.resourceBearerTokenResolver = new ResourceBearerTokenResolver(permitAllUrlProperties);
        this.opaqueTokenIntrospector = opaqueTokenIntrospector;
        log.info("【xht-spring-boot-starter-resource-server】资源服务器配置 初始化 ... <<<<<<");
    }

    /**
     * 资源服务器配置
     *
     * @param http HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception 异常
     */
    @Bean
    @ConditionalOnMissingBean(SecurityFilterChain.class)
    public SecurityFilterChain resourceServerSecurityFilterChain(HttpSecurity http) throws Exception {
        CustomAuthorizeHttpRequestsConfigurer requestsConfigurer = new CustomAuthorizeHttpRequestsConfigurer(permitAllUrlProperties);
        // @formatter:off
        http
                .authorizeHttpRequests(requestsConfigurer)
                .requestCache(AbstractHttpConfigurer::disable)
                .securityContext(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .oauth2ResourceServer(configurer -> {
                    configurer.opaqueToken(opaqueToken -> opaqueToken.introspector(opaqueTokenIntrospector));
                    configurer.authenticationEntryPoint(new XhtAuthenticationEntryPoint());
                    configurer.bearerTokenResolver(resourceBearerTokenResolver);
                })
                .exceptionHandling(handlingConfigurer -> {
                    handlingConfigurer.authenticationEntryPoint(new XhtAuthenticationEntryPoint());//请求未认证的接口
                    handlingConfigurer.accessDeniedHandler(new XhtAccessDeniedHandler());// 请求未授权的接口
                })
        ;
        // @formatter:on
        return http.build();
    }

}
