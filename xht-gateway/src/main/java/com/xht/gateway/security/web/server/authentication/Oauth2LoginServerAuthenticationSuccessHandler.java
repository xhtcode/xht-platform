package com.xht.gateway.security.web.server.authentication;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.DefaultServerRedirectStrategy;
import org.springframework.security.web.server.ServerRedirectStrategy;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * 描述：
 *
 * @author xht
 **/
@Slf4j
@Setter
public class Oauth2LoginServerAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

    private ServerRedirectStrategy redirectStrategy = new DefaultServerRedirectStrategy();

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        ServerWebExchange exchange = webFilterExchange.getExchange();
        ServerHttpResponse response = exchange.getResponse();
        log.info("登录成功 authentication={}", authentication.getClass());
        // WebFluxUtils.webFluxResponseWriter(response, R.ok().build(authentication));
        return redirectStrategy.sendRedirect(exchange, URI.create("http://www.xht.com:"));
    }
}
