package com.xht.auth.security.web.authentication;

import com.xht.auth.captcha.exception.CaptchaException;
import com.xht.auth.security.LoginLogUtils;
import com.xht.auth.security.web.authentication.qr.exception.QyLoginException;
import com.xht.framework.core.domain.R;
import com.xht.framework.core.utils.ServletUtil;
import com.xht.framework.security.exception.BasicAuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * 扫码登录失败处理器
 *
 * @author xht
 **/
@Slf4j
public class AuthorizationServerFailureHandler implements AuthenticationFailureHandler {

    /**
     * Called when an authentication attempt fails.
     *
     * @param request   the request during which the authentication attempt occurred.
     * @param response  the response.
     * @param exception the exception which was thrown to reject the authentication
     *                  request.
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        log.error("登录失败 {} ", exception.getClass().getName(), exception);
        Authentication authenticationRequest = exception.getAuthenticationRequest();
        String errorMessage = "用户名或者密码错误";
        if (exception instanceof QyLoginException || exception instanceof BasicAuthenticationException || exception instanceof CaptchaException) {
            errorMessage = exception.getMessage();
        }
        LoginLogUtils.saveErrorLog(request, authenticationRequest, errorMessage);
        ServletUtil.writeJson(response, R.error().msg(errorMessage).build());
    }

}
