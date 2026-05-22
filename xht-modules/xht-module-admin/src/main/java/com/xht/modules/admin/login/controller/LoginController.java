package com.xht.modules.admin.login.controller;

import com.xht.framework.oauth2.token.response.AbstractOauth2Response;
import com.xht.framework.security.annotation.IgnoreAuth;
import com.xht.modules.admin.login.domain.request.FormLoginRequest;
import com.xht.modules.admin.login.domain.request.PhoneLoginRequest;
import com.xht.modules.admin.login.service.ILoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理员登录
 *
 * @author xht
 **/
@Tag(name = "管理员登录")
@RestController
@RequestMapping("/admin/login")
@RequiredArgsConstructor
public class LoginController {

    private final ILoginService loginService;

    /**
     * 账号密码登录
     *
     * @param formLoginRequest 账号密码登录请求参数，包含用户名、密码、验证码等信息
     * @return OAuth2响应对象，包含访问令牌等信息
     */
    @IgnoreAuth(aop = false)
    @PostMapping("/form")
    @Operation(summary = "账号密码登录")
    public AbstractOauth2Response formLogin(@RequestBody FormLoginRequest formLoginRequest) {
        return loginService.formLogin(formLoginRequest);
    }

    /**
     * 手机号登录
     *
     * @param phoneLoginRequest 手机号登录请求参数，包含手机号和验证码信息
     * @return OAuth2响应对象，包含访问令牌等信息
     */
    @IgnoreAuth(aop = false)
    @PostMapping("/phone")
    @Operation(summary = "手机号登录")
    public AbstractOauth2Response phoneLogin(@RequestBody PhoneLoginRequest phoneLoginRequest) {
        return loginService.phoneLogin(phoneLoginRequest);
    }

}
