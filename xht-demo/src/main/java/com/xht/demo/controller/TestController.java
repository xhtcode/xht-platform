package com.xht.demo.controller;

import com.xht.framework.core.domain.R;
import com.xht.framework.log.annotations.BLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public R<String> test() {
        return R.ok().build("Hello World");
    }

    @Operation(summary = "测试异常接口", description = "测试异常接口描述")
    @GetMapping("/test/error")
    public R<Void> error() {
        System.out.println(1 / 0);
        return R.ok().build();
    }

    // 访问该接口，自动返回授权服务器的用户信息
    @GetMapping("/user/info")
    public R<String> getUserInfo(@RegisteredOAuth2AuthorizedClient("spring")
                                 OAuth2AuthorizedClient authorizedClient) {
        return R.ok().build(authorizedClient.getAccessToken().getTokenValue());
    }
}
