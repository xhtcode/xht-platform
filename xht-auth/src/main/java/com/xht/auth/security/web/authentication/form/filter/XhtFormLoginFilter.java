package com.xht.auth.security.web.authentication.form.filter;

import com.xht.auth.captcha.enums.CaptchaBusinessTypeEnums;
import com.xht.auth.security.web.authentication.AbstractXhtAuthenticationFilter;
import com.xht.auth.security.web.authentication.form.token.XhtFormLoginToken;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.security.exception.BasicAuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * 表单登录过滤器
 *
 * @author xht
 **/
@Setter
public class XhtFormLoginFilter extends AbstractXhtAuthenticationFilter {

    /**
     * 用户名字段参数名
     */
    private String usernameParameter;

    /**
     * 密码字段参数名
     */
    private String passwordParameter;

    /**
     * 验证码键字段参数名
     */
    private String captchaKeyParameter;

    /**
     * 验证码值字段参数名
     */
    private String captchaCodeParameter;

    /**
     * 表单登录过滤器
     * @param loginUrl 登录URL
     */
    public XhtFormLoginFilter(String loginUrl) {
        super(loginUrl);
    }

    /**
     * 执行表单登录认证逻辑
     * <p>
     * 该方法处理基于表单的用户认证请求，从解析后的请求对象中提取认证相关信息，
     * 创建认证令牌并调用认证管理器进行身份验证。
     * </p>
     *
     * @param request HTTP请求对象，包含客户端发送的请求信息
     * @param response HTTP响应对象，用于向客户端返回响应
     * @return 认证通过后的Authentication对象，包含用户身份和权限信息；如果认证失败或未完成则返回null
     * @throws AuthenticationException 当认证过程中发生错误时抛出异常，包括用户名或密码无效等情况
     */
    @Override
    protected Authentication xhtAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = obtainParameter(request, usernameParameter);
        String password = obtainParameter(request, passwordParameter);
        String captchaKey = obtainParameter(request, captchaKeyParameter);
        String captchaCode = obtainParameter(request, captchaCodeParameter);
        ThrowUtils.hasText(username, () -> new BasicAuthenticationException("参数错误：用户名不能为空"));
        ThrowUtils.hasText(password, () -> new BasicAuthenticationException("参数错误：密码不能为空"));
        ThrowUtils.hasText(captchaKey, () -> new BasicAuthenticationException("参数错误：验证码key不能为空"));
        ThrowUtils.hasText(captchaCode, () -> new BasicAuthenticationException("参数错误：验证码不能为空"));
        iCaptchaService.checkCaptcha(captchaKey, captchaCode, CaptchaBusinessTypeEnums.SSO);
        XhtFormLoginToken authRequest = XhtFormLoginToken.unauthenticated(username, password);
        setDetails(request, authRequest);
        return getAuthenticationManager().authenticate(authRequest);
    }


}
