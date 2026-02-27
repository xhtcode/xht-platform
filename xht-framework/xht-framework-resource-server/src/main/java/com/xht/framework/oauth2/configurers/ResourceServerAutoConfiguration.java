package com.xht.framework.oauth2.configurers;

import com.xht.framework.oauth2.handler.ResourceAuthenticationEntryPoint;
import com.xht.framework.oauth2.handler.ResourceBearerTokenResolver;
import com.xht.framework.security.configurers.CustomAuthorizeHttpRequestsConfigurer;
import com.xht.framework.security.properties.PermitAllUrlProperties;
import com.xht.framework.security.web.Http401UnauthorizedEntryPoint;
import com.xht.framework.security.web.access.Http401AccessDeniedHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
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

    private final ResourceAuthenticationEntryPoint resourceAuthenticationEntryPoint;

    private final ResourceBearerTokenResolver resourceBearerTokenResolver;

    private final OpaqueTokenIntrospector opaqueTokenIntrospector;


    public ResourceServerAutoConfiguration(PermitAllUrlProperties permitAllUrlProperties,
                                           ResourceAuthenticationEntryPoint resourceAuthenticationEntryPoint,
                                           ResourceBearerTokenResolver resourceBearerTokenResolver,
                                           OpaqueTokenIntrospector opaqueTokenIntrospector
    ) {
        this.permitAllUrlProperties = permitAllUrlProperties;
        this.resourceAuthenticationEntryPoint = resourceAuthenticationEntryPoint;
        this.resourceBearerTokenResolver = resourceBearerTokenResolver;
        this.opaqueTokenIntrospector = opaqueTokenIntrospector;
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
    @Order(value = Ordered.LOWEST_PRECEDENCE - (Ordered.LOWEST_PRECEDENCE / 2))
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
                .httpBasic(AbstractHttpConfigurer::disable)
                .oauth2ResourceServer(configurer -> {
                    configurer.opaqueToken(opaqueToken -> opaqueToken.introspector(opaqueTokenIntrospector));
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

}
