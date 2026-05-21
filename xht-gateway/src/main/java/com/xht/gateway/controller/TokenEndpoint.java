package com.xht.gateway.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestController
@RequiredArgsConstructor
public class TokenEndpoint {

    /**
     * 获取管理员OAuth2认证信息
     * <p>
     * 该接口用于返回当前已认证的管理员用户的OAuth2授权客户端信息和用户详情。
     * 主要用于调试和验证OAuth2认证流程是否正确执行。
     * </p>
     *
     * @param authorizedClient 已注册的OAuth2授权客户端对象，包含访问令牌等认证信息
     * @param oauth2User       当前认证通过的OAuth2用户主体，包含用户详细信息
     * @return 包含认证信息的Map对象，包括：
     *         - authorizedClient: OAuth2授权客户端对象
     *         - token: 访问令牌对象
     *         - oauth2User: OAuth2用户信息对象
     */
    @GetMapping(path = "/get/token")
    public Map<String, Object> getToken(
            @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
            @AuthenticationPrincipal OAuth2User oauth2User) {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("authorizedClient", authorizedClient);
        objectMap.put("token", authorizedClient.getAccessToken());
        objectMap.put("oauth2User", oauth2User);
        return objectMap;
    }

}
