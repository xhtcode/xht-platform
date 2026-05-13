package com.xht.demo.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.log.annotations.BLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xht
 **/
@Tag(name = "测试模块")
@RestController
@RequiredArgsConstructor
public class TestController {

    @BLog(value = "测试", description = "测试")
    @Operation(summary = "测试接口", description = "测试接口描述")
    @GetMapping
    public R<Object> test( @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
                            @AuthenticationPrincipal OAuth2User oauth2User) {
        Map<String, Object> map = new HashMap<>();
        map.put("userName", oauth2User.getName());
        map.put("clientName", authorizedClient.getClientRegistration().getClientName());
        map.put("userAttributes", oauth2User.getAttributes());
        map.put("accessToken", authorizedClient.getAccessToken().getTokenValue());
        map.put("authorizedClient", authorizedClient);
        map.put("oauth2User", oauth2User);
        return R.ok().build(map);
    }

    @Operation(summary = "测试异常接口", description = "测试异常接口描述")
    @GetMapping("/test/error")
    public R<Void> error() {
        return R.ok().build();
    }

    // 访问该接口，自动返回授权服务器的用户信息
    @GetMapping("/user/info")
    public R<String> getUserInfo(@RegisteredOAuth2AuthorizedClient("spring")
                                 OAuth2AuthorizedClient authorizedClient) {
        return R.ok().build(authorizedClient.getAccessToken().getTokenValue());
    }
}
