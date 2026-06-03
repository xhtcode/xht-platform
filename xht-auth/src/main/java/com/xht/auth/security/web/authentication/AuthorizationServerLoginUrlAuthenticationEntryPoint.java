package com.xht.auth.security.web.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 授权服务器登录入口点
 * 未认证时自动重定向到登录页，并携带原始请求地址作为回调目标
 *
 * @author xht
 */
@Slf4j
public class AuthorizationServerLoginUrlAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * 重定向策略
     */
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /**
     * 登录表单地址
     */
    private final String loginFormUrl;

    /**
     * 构造函数（参数校验 + 安全注入）
     */
    public AuthorizationServerLoginUrlAuthenticationEntryPoint(String loginFormUrl) {
        // Spring 官方推荐 Assert 替代自定义工具类
        Assert.hasText(loginFormUrl, "登录页面地址 loginFormUrl 不能为空且不能仅包含空白字符");
        this.loginFormUrl = loginFormUrl;
    }

    /**
     * 未认证时触发：重定向到登录页
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // 1. 构建原始请求完整 URL（包含查询参数）
        String originalRequestUrl = buildOriginalRequestUrl(request);
        // 2. 编码并拼接目标地址
        String encodedTargetUrl = encodeTargetUrl(originalRequestUrl);
        String redirectUrl = loginFormUrl + "?target=" + encodedTargetUrl;
        // 3. 日志记录（生产环境可调整级别）
        log.debug("未认证请求，重定向到登录页面：{}", redirectUrl);
        // 4. 执行重定向
        redirectStrategy.sendRedirect(request, response, redirectUrl);
    }

    /**
     * 构建原始请求完整 URL（域名 + 路径 + 查询参数）
     */
    private String buildOriginalRequestUrl(HttpServletRequest request) {
        StringBuffer requestUrl = request.getRequestURL();
        String queryString = request.getQueryString();

        if (!ObjectUtils.isEmpty(queryString)) {
            requestUrl.append('?').append(queryString);
        }
        return requestUrl.toString();
    }

    /**
     * 对回调地址进行 UTF-8 编码（安全、规范）
     */
    private String encodeTargetUrl(String targetUrl) {
        return UriUtils.encode(targetUrl, StandardCharsets.UTF_8);
    }

}
