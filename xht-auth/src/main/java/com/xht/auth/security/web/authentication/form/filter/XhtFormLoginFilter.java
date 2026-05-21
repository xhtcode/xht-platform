package com.xht.auth.security.web.authentication.form.filter;

import com.xht.auth.captcha.enums.CaptchaBusinessTypeEnums;
import com.xht.auth.security.web.authentication.AbstractXhtAuthenticationFilter;
import com.xht.auth.security.web.authentication.form.token.XhtFormLoginToken;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.security.exception.BasicAuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;
import org.springframework.security.core.AuthenticationException;

/**
 * 表单登录过滤器
 *
 * @author xht
 **/
@Setter
public class XhtFormLoginFilter extends AbstractXhtAuthenticationFilter<XhtFormLoginToken> {

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
     * 创建认证Token对象
     * <p>
     * 该方法由子类实现，用于从HTTP请求中提取认证信息并创建对应的认证Token对象。
     * 通常包括获取用户名、密码、验证码等认证凭据，并封装为XhtFormLoginToken实例。
     * </p>
     *
     * @param request HTTP请求对象，包含认证所需的参数
     * @return 认证Token对象，用于后续的身份验证流程
     */
    @Override
    protected XhtFormLoginToken createAuthenticationToken(HttpServletRequest request) {
        String username = obtainParameter(request, usernameParameter);
        String password = obtainParameter(request, passwordParameter);
        XhtFormLoginToken unauthenticated = XhtFormLoginToken.unauthenticated(username, password);
        unauthenticated.setCaptchaKey(obtainParameter(request, captchaKeyParameter));
        unauthenticated.setCaptchaCode(obtainParameter(request, captchaCodeParameter));
        return unauthenticated;
    }

    /**
     * 检查认证Token的有效性
     * <p>
     * 该方法由子类实现，用于在正式认证前对Token进行预检查。
     * 可以包括验证验证码是否正确、检查账号状态、验证请求参数完整性等操作。
     * 如果检查不通过，应抛出相应的认证异常。
     * </p>
     *
     * @param authenticationToken 待检查的认证Token对象
     * @throws AuthenticationException 当Token检查不通过时抛出异常
     */
    @Override
    protected void checkAuthentication(XhtFormLoginToken authenticationToken) {
        String username = authenticationToken.getName();
        String password = StringUtils.str(authenticationToken.getCredentials());
        String captchaKey = authenticationToken.getCaptchaKey();
        String captchaCode = authenticationToken.getCaptchaCode();
        ThrowUtils.hasText(username, () -> new BasicAuthenticationException("参数错误：用户名不能为空"));
        ThrowUtils.hasText(password, () -> new BasicAuthenticationException("参数错误：密码不能为空"));
        ThrowUtils.hasText(captchaKey, () -> new BasicAuthenticationException("参数错误：验证码key不能为空"));
        ThrowUtils.hasText(captchaCode, () -> new BasicAuthenticationException("参数错误：验证码不能为空"));
        iCaptchaService.checkCaptcha(captchaKey, captchaCode, CaptchaBusinessTypeEnums.SSO);
    }


}
