package com.xht.auth.security;

import com.xht.auth.security.oauth2.server.authorization.AbstractAuthenticationToken;
import com.xht.auth.security.web.authentication.AbstractXhtAuthenticationToken;
import com.xht.framework.core.enums.LoginTypeEnums;
import com.xht.framework.core.support.blog.enums.LogStatusEnums;
import com.xht.framework.core.utils.IpUtils;
import com.xht.framework.core.utils.ServletUtil;
import com.xht.framework.core.utils.mdc.TraceIdUtils;
import com.xht.framework.core.utils.spring.SpringContextUtils;
import com.xht.framework.log.event.LoginLogApplicationEvent;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * 登录日志工具类
 *
 * @author xht
 **/
public final class LoginLogUtils {

    /**
     * 保存登录成功日志
     *
     * @param request HTTP请求对象，用于获取请求信息和IP地址
     * @param authentication 认证对象，包含用户身份信息
     */
    public static void saveSuccessLog(HttpServletRequest request, Authentication authentication) {
        String userName = authentication.getName();
        LoginLogApplicationEvent event = new LoginLogApplicationEvent(userName, LogStatusEnums.NORMAL);
        event.setTraceId(TraceIdUtils.getTraceId());
        if (authentication instanceof AbstractXhtAuthenticationToken authenticationToken) {
            event.setLoginType(Optional.of(authenticationToken).map(AbstractXhtAuthenticationToken::getLoginType).map(LoginTypeEnums::getValue).orElse("异常登录"));
        } else {
            event.setLoginType("异常登录");
        }
        event.setLoginTime(LocalDateTime.now());
        event.setLoginIp(IpUtils.getIpAddr(request));
        event.setLoginRequestInfo(Map.of("request", ServletUtil.getParamMap(request), "header", ServletUtil.getHeaderMap(request)));
        SpringContextUtils.publishEvent(event);
    }

    /**
     * 保存登录失败日志
     *
     * @param request HTTP请求对象，用于获取请求信息和IP地址
     * @param authenticationRequest 认证请求对象，包含用户身份信息
     * @param errorMessage 登录失败原因描述
     */
    public static void saveErrorLog(HttpServletRequest request, Authentication authenticationRequest, String errorMessage) {
        if (Objects.isNull(authenticationRequest)) {
            return;
        }
        String userName = authenticationRequest.getName();
        LoginLogApplicationEvent event = new LoginLogApplicationEvent(userName, LogStatusEnums.ERROR);
        event.setTraceId(TraceIdUtils.getTraceId());
        if (authenticationRequest instanceof AbstractXhtAuthenticationToken authenticationToken) {
            event.setLoginType(Optional.of(authenticationToken).map(AbstractXhtAuthenticationToken::getLoginType).map(LoginTypeEnums::getValue).orElse("异常登录"));
        }
        if (authenticationRequest instanceof AbstractAuthenticationToken authenticationToken) {
            event.setLoginType(Optional.of(authenticationToken).map(AbstractAuthenticationToken::getGrantType).map(AuthorizationGrantType::getValue).orElse("异常登录"));
        } else {
            event.setLoginType("异常登录");
        }
        event.setLoginTime(LocalDateTime.now());
        event.setLoginIp(IpUtils.getIpAddr(request));
        event.setLoginRequestInfo(Map.of("request", ServletUtil.getParamMap(request), "header", ServletUtil.getHeaderMap(request)));
        event.setLoginFailReason(errorMessage);
        SpringContextUtils.publishEvent(event);
    }
}
