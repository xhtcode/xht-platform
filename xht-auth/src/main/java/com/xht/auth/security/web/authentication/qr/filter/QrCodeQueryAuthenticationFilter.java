package com.xht.auth.security.web.authentication.qr.filter;

import com.xht.auth.security.web.authentication.qr.domain.QrCodeInfo;
import com.xht.auth.security.web.authentication.qr.exception.QyLoginException;
import com.xht.framework.security.core.userdetails.BasicUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
public class QrCodeQueryAuthenticationFilter extends QrCodeAbstractAuthenticationFilter {

    @Setter
    private RememberMeServices rememberMeServices;


    public QrCodeQueryAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    /**
     * 抽象二维码登录过滤器
     *
     * @param request  请求
     * @param response 响应
     * @param chain    过滤器链
     */
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String username = "测试";
        this.logger.debug(LogMessage.format("Creating new persistent login for user %s", username));
        String qrCodeId = request.getParameter("qrCodeId");
        try {
            QrCodeInfo qrCodeInfo = qrDataManager.getQrCodeInfo(qrCodeId).orElseThrow(() -> new RuntimeException("无效二维码或二维码已过期."));
            // 二维码是否过期
            boolean qrCodeExpire = qrCodeInfo.getExpiresTime().isBefore(LocalDateTime.now());
            if (qrCodeExpire) {
                throw new RuntimeException("二维码已过期.");
            }
            BasicUserDetails userData = qrDataManager.getUserData(qrCodeInfo);
            if (userData == null) {
                throw new RuntimeException("用户不存在.");
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userData, null, userData.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            sessionStrategy.onAuthentication(authenticationToken, request, response);
            rememberMeServices.loginSuccess(request, response, authenticationToken);
            qrDataManager.removeQrInfo(qrCodeInfo);
            successHandler.onAuthenticationSuccess(request, response, authenticationToken);
        } catch (Exception e) {
            log.error("二维码登录失败: {}", e.getMessage(), e);
            AuthenticationException authenticationException = new QyLoginException(e.getMessage(), e);
            failureHandler.onAuthenticationFailure(request, response, authenticationException);
        }
    }


}