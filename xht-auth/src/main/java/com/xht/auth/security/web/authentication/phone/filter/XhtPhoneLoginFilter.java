package com.xht.auth.security.web.authentication.phone.filter;

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
public class XhtPhoneLoginFilter extends AbstractXhtAuthenticationFilter {

    /**
     * 表单登录手机号码字段名
     */
    private String phoneParameter;

    /**
     * 表单登录手机验证码字段名
     */
    private String phoneCodeParameter;

    /**
     * 构造函数，初始化表单登录过滤器的登录处理URL
     * @param loginUrl 表单登录的登录处理URL，不能为空
     */
    public XhtPhoneLoginFilter(String loginUrl) {
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
        String phone = obtainParameter(request, phoneParameter);
        String phoneCode = obtainParameter(request, phoneCodeParameter);
        ThrowUtils.hasText(phone, () -> new BasicAuthenticationException("手机号不能为空"));
        ThrowUtils.hasText(phoneCode, () -> new BasicAuthenticationException("手机验证码不能为空"));
        iCaptchaService.checkPhoneCode(phone, phoneCode);
        XhtFormLoginToken authRequest = XhtFormLoginToken.unauthenticated(phone, phoneCode);
        setDetails(request, authRequest);
        return getAuthenticationManager().authenticate(authRequest);
    }

}
