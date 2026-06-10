package com.xht.gateway.controller;

import com.xht.framework.common.domain.R;
import com.xht.gateway.properties.SecurityGatewayProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.AbstractOAuth2Token;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Slf4j
@Controller
@RequiredArgsConstructor
public class TokenEndpoint {

    private final SecurityGatewayProperties securityGatewayProperties;

    /**
     * 获取管理员OAuth2认证信息
     * <p>
     * 该接口用于返回当前已认证的管理员用户的OAuth2授权客户端信息和用户详情。
     * 主要用于调试和验证OAuth2认证流程是否正确执行。
     * </p>
     *
     * @param authorizedClient 已注册的OAuth2授权客户端对象，包含访问令牌等认证信息
     * @return 包含认证信息的Map对象，包括：
     *         - authorizedClient: OAuth2授权客户端对象
     *         - token: 访问令牌对象
     *         - oauth2User: OAuth2用户信息对象
     */
    @ResponseBody
    @GetMapping(path = "/get/token")
    public R<Object> getToken(
            @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("token", Optional
                .ofNullable(authorizedClient)
                .map(OAuth2AuthorizedClient::getAccessToken).
                map(AbstractOAuth2Token::getTokenValue)
                .orElse(null)
        );
        return R.ok().build(objectMap);
    }

    @GetMapping("/authorized")
    public Mono<Void> root(ServerWebExchange exchange, @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
        log.info("authorizedClient: {}", authorizedClient);
        exchange.getResponse().setStatusCode(HttpStatus.SEE_OTHER);
        exchange.getResponse().getHeaders().setLocation(URI.create(securityGatewayProperties.getAppBaseUri()));
        return exchange.getResponse().setComplete();
    }


}
