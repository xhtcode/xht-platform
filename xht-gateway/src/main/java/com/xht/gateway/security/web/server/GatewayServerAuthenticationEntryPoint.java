package com.xht.gateway.security.web.server;

import com.xht.framework.core.domain.R;
import com.xht.gateway.utils.WebFluxUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.DefaultServerRedirectStrategy;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.ServerRedirectStrategy;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 描述：
 *
 * @author xht
 **/
public class GatewayServerAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    private ServerRedirectStrategy redirectStrategy = new DefaultServerRedirectStrategy();

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        ServerHttpRequest request = exchange.getRequest();
        HttpMethod method = request.getMethod();
        if (Objects.equals(method, HttpMethod.GET)) {
            return redirectStrategy.sendRedirect(exchange, URI.create("http://www.xht.com:3000/oauth2"));
        }
        ServerHttpResponse response = exchange.getResponse();
        Map<String, Object> data = new HashMap<>();
        data.put("targetUrl", "http://www.xht.com:8080/get/token");
        return WebFluxUtils.webFluxResponseWriter(response, R.ok().msg("未登录的时候要跳转").build(data));
    }
}
