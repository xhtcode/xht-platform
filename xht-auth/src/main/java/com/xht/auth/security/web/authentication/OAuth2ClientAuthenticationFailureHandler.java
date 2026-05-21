package com.xht.auth.security.web.authentication;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.utils.ServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
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
        OAuth2Error error = ((OAuth2AuthenticationException) exception).getError();
        HttpStatus unauthorized;
        if (OAuth2ErrorCodes.INVALID_CLIENT.equals(error.getErrorCode())) {
            unauthorized = HttpStatus.UNAUTHORIZED;
        } else {
            unauthorized = HttpStatus.BAD_REQUEST;
        }
        ServletUtil.writeJson(response, unauthorized, R.error().msg("客户端不存在或密钥错误").build());
    }

}
