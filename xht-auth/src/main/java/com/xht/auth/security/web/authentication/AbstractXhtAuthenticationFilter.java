package com.xht.auth.security.web.authentication;

import com.xht.auth.captcha.service.ICaptchaService;
import com.xht.framework.core.constant.HttpConstants;
import jakarta.servlet.ServletException;
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

import java.io.IOException;

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
public abstract class AbstractXhtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    protected ICaptchaService iCaptchaService;

    protected AbstractXhtAuthenticationFilter(String pathUrl) {
        super(PathPatternRequestMatcher.withDefaults()
                .matcher(pathUrl));
    }

    @Override
    public final Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        validateRequestMethod(request);
        return xhtAuthentication(request, response);
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
    protected abstract Authentication xhtAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException;

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
    protected final void setDetails(HttpServletRequest request, AbstractAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

}
