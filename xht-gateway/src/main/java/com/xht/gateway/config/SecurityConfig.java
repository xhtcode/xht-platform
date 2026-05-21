package com.xht.gateway.config;

import com.xht.framework.core.domain.R;
import com.xht.gateway.properties.SecurityGatewayProperties;
import com.xht.gateway.utils.WebFluxUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author Joe Grandja
 * @since 1.4
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final SecurityGatewayProperties securityGatewayProperties;

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
        http.cors(ServerHttpSecurity.CorsSpec::disable);
        http.httpBasic(ServerHttpSecurity.HttpBasicSpec::disable);
        http.formLogin(ServerHttpSecurity.FormLoginSpec::disable);
        http.sessionManagement(c -> {

        });
        http
                .authorizeExchange(authorize ->
                        authorize
                                .pathMatchers(securityGatewayProperties.getAuthenticatedUrls()).authenticated()
                                .anyExchange().permitAll()
                );
        http.oauth2Login(oauth2Login -> oauth2Login.authenticationFailureHandler((webFilterExchange, exception) -> {
            ServerWebExchange exchange = webFilterExchange.getExchange();
            ServerHttpResponse response = exchange.getResponse();
            log.error("登录失败 {}", exception.getLocalizedMessage(), exception);
            return WebFluxUtils.webFluxResponseWriter(response, R.error().build(exception.getMessage()));
        }));
        return http.build();
    }


}
