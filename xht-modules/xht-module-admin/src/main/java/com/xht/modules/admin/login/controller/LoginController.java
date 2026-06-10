package com.xht.modules.admin.login.controller;

import com.xht.framework.common.domain.R;
import com.xht.framework.security.annotation.IgnoreAuth;
import com.xht.modules.admin.login.domain.form.PasswordLoginForm;
import com.xht.modules.admin.login.domain.form.PhoneLoginForm;
import com.xht.modules.admin.login.domain.response.LoginResponse;
import com.xht.modules.admin.login.service.ILoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final ILoginService loginService;

    /**
     * 账号密码登录
     *
     * @param servletRequest HTTP请求对象
     * @param loginForm 账号密码登录请求参数，包含用户名、密码、验证码等信息
     * @return OAuth2响应对象，包含访问令牌等信息
     */
    @IgnoreAuth(aop = false)
    @PostMapping("/form")
    @Operation(summary = "账号密码登录")
    public R<LoginResponse> formLogin(HttpServletRequest servletRequest, @RequestBody PasswordLoginForm loginForm) {
        return R.ok().build(loginService.formLogin(servletRequest,loginForm));
    }

    /**
     * 手机号登录
     *
     * @param servletRequest HTTP请求对象
     * @param phoneLoginForm 手机号登录请求参数，包含手机号和验证码信息
     * @return OAuth2响应对象，包含访问令牌等信息
     */
    @IgnoreAuth(aop = false)
    @PostMapping("/phone")
    @Operation(summary = "手机号登录")
    public R<LoginResponse> phoneLogin(HttpServletRequest servletRequest, @RequestBody PhoneLoginForm phoneLoginForm) {
        return R.ok().build(loginService.phoneLogin(servletRequest,phoneLoginForm));
    }

}
