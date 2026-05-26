package com.xht.modules.admin.login.service;

import com.xht.modules.admin.login.domain.form.PasswordLoginForm;
import com.xht.modules.admin.login.domain.form.PhoneLoginForm;
import com.xht.modules.admin.login.domain.response.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 登录服务类
 *
 * @author xht
 **/
public interface ILoginService {

    /**
     * 表单登录
     *
     * @param servletRequest HTTP请求对象
     * @param passwordLoginForm 表单登录请求参数，包含用户名、密码、验证码等信息
     * @return OAuth2响应对象，包含访问令牌等信息
     */
    LoginResponse formLogin(HttpServletRequest servletRequest, PasswordLoginForm passwordLoginForm);

    /**
     * 手机号登录
     *
     * @param servletRequest HTTP请求对象
     * @param phoneLoginForm 手机号登录请求参数，包含手机号和验证码信息
     * @return OAuth2响应对象，包含访问令牌等信息
     */
    LoginResponse phoneLogin(HttpServletRequest servletRequest,PhoneLoginForm phoneLoginForm);

}
