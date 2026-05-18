package com.xht.auth.security.web.authentication.qr.filter;

import com.xht.auth.security.web.authentication.qr.manager.AbstractQrManager;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 抽象二维码登录过滤器
 *
 * @author xht
 **/
@Slf4j
@Setter
public abstract class QrCodeAbstractAuthenticationFilter extends OncePerRequestFilter {

    @Getter
    private final RequestMatcher requiresAuthenticationRequestMatcher;

    protected AbstractQrManager qrDataManager;

    protected SessionAuthenticationStrategy sessionStrategy;

    protected AuthenticationSuccessHandler successHandler;

    protected AuthenticationFailureHandler failureHandler;

    protected QrCodeAbstractAuthenticationFilter(String defaultFilterProcessesUrl) {
        this.requiresAuthenticationRequestMatcher = PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST, defaultFilterProcessesUrl);
    }


    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain filterChain) throws ServletException, IOException {
        if (!getRequiresAuthenticationRequestMatcher().matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        doFilter(request, (HttpServletResponse) response, filterChain);
    }


    /**
     * 抽象二维码登录过滤器
     * @param request 请求
     * @param response 响应
     * @param chain 过滤器链
     * @throws IOException 抛出IO异常
     * @throws ServletException 抛出Servlet异常
     */
    protected abstract void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException;

}
