package com.xht.auth.security.web.authentication;

import com.xht.auth.captcha.exception.CaptchaException;
import com.xht.auth.security.web.authentication.qr.exception.QyLoginException;
import com.xht.framework.core.domain.R;
import com.xht.framework.core.enums.LoginTypeEnums;
import com.xht.framework.core.support.blog.enums.LogStatusEnums;
import com.xht.framework.core.utils.IpUtils;
import com.xht.framework.core.utils.ServletUtil;
import com.xht.framework.core.utils.mdc.TraceIdUtils;
import com.xht.framework.core.utils.spring.SpringContextUtils;
import com.xht.framework.log.event.LoginLogApplicationEvent;
import com.xht.framework.security.exception.BasicAuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

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
        saveErrorLog(request, authenticationRequest, errorMessage);
        ServletUtil.writeJson(response, R.error().msg(errorMessage).build());
    }

    /**
     * 保存登录日志
     *
     * @param authenticationRequest 认证请求
     * @param request               请求
     */
    private void saveErrorLog(HttpServletRequest request, Authentication authenticationRequest, String errorMessage) {
        if (Objects.isNull(authenticationRequest)) {
            return;
        }
        String userName = authenticationRequest.getName();
        LoginLogApplicationEvent event = new LoginLogApplicationEvent(userName, LogStatusEnums.ERROR);
        event.setTraceId(TraceIdUtils.getTraceId());
        if (authenticationRequest instanceof AbstractXhtAuthenticationToken authenticationToken) {
            LoginTypeEnums loginType = authenticationToken.getLoginType();
            event.setLoginType(loginType);
        }
        event.setLoginTime(LocalDateTime.now());
        event.setLoginIp(IpUtils.getIpAddr(request));
        event.setLoginRequestInfo(Map.of("request", ServletUtil.getParamMap(request), "header", ServletUtil.getHeaderMap(request)));
        event.setLoginFailReason(errorMessage);
        SpringContextUtils.publishEvent(event);
    }

}
