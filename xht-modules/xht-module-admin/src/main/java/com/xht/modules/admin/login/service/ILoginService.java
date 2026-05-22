package com.xht.modules.admin.login.service;

import com.xht.framework.oauth2.token.response.AbstractOauth2Response;
import com.xht.modules.admin.login.domain.request.FormLoginRequest;
import com.xht.modules.admin.login.domain.request.PhoneLoginRequest;

/**
 * 登录服务类
 *
 * @author xht
 **/
public interface ILoginService {

    /**
     * 表单登录
     *
     * @param formLoginRequest 表单登录请求参数，包含用户名、密码、验证码等信息
     * @return OAuth2响应对象，包含访问令牌等信息
     */
    AbstractOauth2Response formLogin(FormLoginRequest formLoginRequest);

    /**
     * 手机号登录
     *
     * @param phoneLoginRequest 手机号登录请求参数，包含手机号和验证码信息
     * @return OAuth2响应对象，包含访问令牌等信息
     */
    AbstractOauth2Response phoneLogin(PhoneLoginRequest phoneLoginRequest);

}
