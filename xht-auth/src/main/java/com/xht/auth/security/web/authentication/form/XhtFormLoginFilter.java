package com.xht.auth.security.web.authentication.form;

import com.xht.auth.security.web.authentication.AbstractXhtAuthenticationFilter;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.security.exception.BasicAuthenticationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;

/**
 * 表单登录过滤器
 *
 * @author xht
 **/
public class XhtFormLoginFilter extends AbstractXhtAuthenticationFilter {


    protected XhtFormLoginFilter(String loginUrl) {
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
     * @throws IOException 当读取请求数据或写入响应数据发生IO错误时抛出异常
     * @throws ServletException 当Servlet处理过程中发生错误时抛出异常
     */
    @Override
    protected Authentication xhtAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String username = obtainParameter(request, "username");
        String password = obtainParameter(request, "password");
        String captchaKey = obtainParameter(request, "captchaKey");
        String captchaCode = obtainParameter(request, "captchaCode");
        ThrowUtils.hasText(username, () -> new BasicAuthenticationException("用户名不能为空"));
        ThrowUtils.hasText(password, () -> new BasicAuthenticationException("密码不能为空"));
        ThrowUtils.hasText(captchaKey, () -> new BasicAuthenticationException("验证码key不能为空"));
        ThrowUtils.hasText(captchaCode, () -> new BasicAuthenticationException("验证码不能为空"));
        iCaptchaService.checkCaptcha(captchaKey, captchaCode);
        XhtFormLoginToken authRequest = XhtFormLoginToken.unauthenticated(username, password);
        setDetails(request, authRequest);
        return getAuthenticationManager().authenticate(authRequest);
    }


}
