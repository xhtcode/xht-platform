package com.xht.auth.security.web.authentication.phone.filter;

import com.xht.auth.captcha.enums.CaptchaBusinessTypeEnums;
import com.xht.auth.security.web.authentication.AbstractXhtAuthenticationFilter;
import com.xht.auth.security.web.authentication.phone.token.XhtPhoneLoginToken;
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
public class XhtPhoneLoginFilter extends AbstractXhtAuthenticationFilter<XhtPhoneLoginToken> {

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
    protected XhtPhoneLoginToken createAuthenticationToken(HttpServletRequest request) {
        String phone = obtainParameter(request, phoneParameter);
        String phoneCode = obtainParameter(request, phoneCodeParameter);
        return XhtPhoneLoginToken.unauthenticated(phone, phoneCode);
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
    protected void checkAuthentication(XhtPhoneLoginToken authenticationToken) {
        String phone = authenticationToken.getName();
        String phoneCode = StringUtils.str(authenticationToken.getCredentials());
        ThrowUtils.hasText(phone, () -> new BasicAuthenticationException("参数错误：手机号不能为空"));
        ThrowUtils.hasText(phoneCode, () -> new BasicAuthenticationException("参数错误：手机验证码不能为空"));
        iCaptchaService.checkPhoneCode(phone, phoneCode, CaptchaBusinessTypeEnums.SSO);
    }

}
