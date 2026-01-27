package com.xht.auth.security.web.authentication;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.utils.ServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * OAuth 2.0客户端的身份验证请求 认证失败处理
 *
 * @author xht
 **/
@Slf4j
public class OAuth2ClientAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        SecurityContextHolder.clearContext();
        log.error("OAuth 2.0客户端的身份验证请求 认证失败: {}", exception.getMessage(), exception);
        ServletUtil.writeJson(response, R.errorMsg("错误的请求"));
    }
}
