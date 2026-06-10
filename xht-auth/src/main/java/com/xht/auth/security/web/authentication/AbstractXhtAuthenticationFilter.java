package com.xht.auth.security.web.authentication;

import com.xht.auth.captcha.service.ICaptchaService;
import com.xht.framework.common.constant.HttpConstants;
import com.xht.framework.exception.utils.ThrowUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

import java.util.Objects;

/**
 * 认证过滤器抽象类
 * <p>
 * 该类提供了Spring Security认证过滤器的通用实现框架，定义了完整的认证流程：
 * <ul>
 *     <li>判断请求是否需要进行认证</li>
 *     <li>执行认证逻辑（由子类实现）</li>
 *     <li>处理认证成功的情况（设置SecurityContext、会话管理、记住我、调用成功处理器）</li>
 *     <li>处理认证失败的情况（清除SecurityContext、调用失败处理器）</li>
 * </ul>
 * 子类需要实现{@link #attemptAuthentication(HttpServletRequest, HttpServletResponse)}方法来完成具体的认证逻辑。
 * </p>
 *
 * @author xht
 **/
@Slf4j
@Setter
public abstract class AbstractXhtAuthenticationFilter<T extends AbstractXhtAuthenticationToken> extends AbstractAuthenticationProcessingFilter {

    protected ICaptchaService iCaptchaService;

    protected AbstractXhtAuthenticationFilter(String pathUrl) {
        super(PathPatternRequestMatcher.withDefaults()
                .matcher(pathUrl));
    }

    /**
     * <p>
     * 该方法用于执行具体的认证逻辑。它从HTTP请求中提取认证信息，创建认证Token对象，并调用认证管理器进行认证。
     * 认证成功后，设置SecurityContext并调用成功处理器；认证失败时，清除SecurityContext并调用失败处理器。
     * </p>
     *
     * @param request  from which to extract parameters and perform the authentication
     * @param response the response, which may be needed if the implementation has to do a
     *                 redirect as part of a multi-stage authentication process (such as OIDC).
     * @return the authenticated object
     * @throws AuthenticationException if authentication fails
     */
    @Override
    public final Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        validateRequestMethod(request);
        T authenticationToken = null;
        try {
            authenticationToken = createAuthenticationToken(request);
            boolean authenticated = authenticationToken.isAuthenticated();
            ThrowUtils.throwIf(authenticated, () -> new AuthenticationServiceException("Prohibit the creation of already-verified AbstractXhtAuthenticationToken"));
            checkAuthentication(authenticationToken);
            setDetails(request, authenticationToken);
            return getAuthenticationManager().authenticate(authenticationToken);
        } catch (AuthenticationException e) {
            log.error("Authentication request failed: ", e);
            if (Objects.nonNull(authenticationToken)) {
                e.setAuthenticationRequest(authenticationToken);
            }
            throw e;
        }
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
    protected abstract T createAuthenticationToken(HttpServletRequest request);

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
    protected abstract void checkAuthentication(T authenticationToken);


    /**
     * 验证HTTP请求方法是否符合要求
     * <p>
     * 该方法用于检查当前HTTP请求的方法（GET、POST等）是否与预期的方法匹配。
     * 如果指定了期望的HTTP方法且实际请求方法不匹配，则抛出认证服务异常。
     * 这通常用于确保认证请求使用了正确的HTTP方法，增强安全性。
     * </p>
     *
     * @param request HTTP请求对象，从中获取实际的请求方法
     * @throws AuthenticationServiceException 当请求方法与期望方法不匹配时抛出异常
     */
    protected final void validateRequestMethod(HttpServletRequest request) {
        // 验证指定的HTTP方法是否不为空，且实际请求方法与期望方法不一致
        if (!request.getMethod().equals(HttpConstants.Method.POST.getValue())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
    }

    /**
     * 获取请求参数
     *
     * @param request 请求对象
     * @param name    参数名称
     * @return 参数值
     */
    protected final String obtainParameter(HttpServletRequest request, String name) {
        return request.getParameter(name);
    }

    /**
     * 以便子类能够配置将放入身份验证请求的“详细信息”属性中的内容。*
     *
     * @param request：用于创建身份验证请求的对象
     * @param authRequest：该身份验证请求对象，其详细信息应已完成设置
     */
    void setDetails(HttpServletRequest request, AbstractAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

}
