package com.xht.framework.security.web;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.exception.code.GlobalErrorStatusCode;
import com.xht.framework.core.utils.ServletUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * 没带 Token / Token 过期 / Token 非法 → 根本没登录成功
 *
 * @author xht
 **/
@Slf4j
public class XhtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String error = buildErrorMessage(authException);
        GlobalErrorStatusCode unauthorized = GlobalErrorStatusCode.UNAUTHORIZED;
        if (authException instanceof InsufficientAuthenticationException || authException instanceof OAuth2AuthenticationException) {
            unauthorized = GlobalErrorStatusCode.TOKEN_EXPIRED;
            error = unauthorized.getMsg();
        }
        log.debug("身份验证失败处理器: ex_class={} message={}", authException.getClass().getName(), error, authException);
        ServletUtil.writeJson(response, HttpStatus.UNAUTHORIZED, R
                .ok()
                .info(unauthorized)
                .msg(error)
                .build()
        );
    }

    protected String buildErrorMessage(AuthenticationException authException) {
        String error = "认证失败：" + authException.getMessage();
        if (authException instanceof BadCredentialsException || authException instanceof UsernameNotFoundException) {
            error = "用户名或密码错误";
        }
        if (authException instanceof LockedException) {
            error = "用户账户已被锁定";
        }
        if (authException instanceof DisabledException) {
            error = "用户账户已禁用";
        }
        if (authException instanceof CredentialsExpiredException || authException instanceof AccountExpiredException) {
            error = "用户账户已过期";
        }
        return error;
    }

}
