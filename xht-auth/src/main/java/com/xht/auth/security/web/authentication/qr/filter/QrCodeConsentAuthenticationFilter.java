package com.xht.auth.security.web.authentication.qr.filter;

import com.xht.auth.security.web.authentication.qr.domain.QrCodeInfo;
import com.xht.auth.security.web.authentication.qr.enums.QrCodeStatusEnums;
import com.xht.auth.security.web.authentication.qr.exception.QyLoginException;
import com.xht.framework.core.domain.R;
import com.xht.framework.core.utils.ServletUtil;
import com.xht.framework.oauth2.utils.SecurityUtils;
import com.xht.framework.security.core.userdetails.BasicUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;

/**
 * 二维码扫码登录 授权过滤器
 *
 * @author xht
 */
@Slf4j
public class QrCodeConsentAuthenticationFilter extends QrCodeAbstractAuthenticationFilter {

    public QrCodeConsentAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    /**
     * 抽象二维码登录过滤器
     *
     * @param request  请求
     * @param response 响应
     * @param chain    过滤器链
     * @throws IOException      抛出IO异常
     * @throws ServletException 抛出Servlet异常
     */
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String qrCodeId = request.getParameter("qrCodeId");
        String loginConsentStr = request.getParameter("loginConsent");
        try {
            boolean loginConsent = Boolean.parseBoolean(loginConsentStr);
            String qrCodeTicket = request.getParameter("qrCodeTicket");
            BasicUserDetails user = SecurityUtils.getUser();
            // 校验二维码状态
            QrCodeInfo qrCodeInfo = qrDataManager.getQrCodeInfo(qrCodeId).orElseThrow(() -> new QyLoginException("无效二维码或二维码已过期."));
            if (loginConsent) {
                qrDataManager.removeQrInfo(qrCodeInfo);
                throw new QyLoginException("用户取消了授权");
            }
            if (!Objects.equals(qrCodeInfo.getQrCodeStatus(), QrCodeStatusEnums.SCANNED)) {
                throw new QyLoginException("二维码已被其他人扫描，无法重复扫描.");
            }
            if (!Objects.equals(qrCodeInfo.getQrCodeTicket(), qrCodeTicket)) {
                throw new QyLoginException("登录确认失败，请重新扫描二维码.");
            }
            if (!Objects.equals(qrCodeInfo.getName(), user.getName())) {
                throw new QyLoginException("非法请求，二维码盗刷");
            }
            // 更新二维码信息的状态
            qrCodeInfo.setQrCodeStatus(QrCodeStatusEnums.CONFIRMED);
            // 存储用户信息 更新二维码信息
            qrDataManager.saveUserData(qrCodeInfo, user);
            qrDataManager.updateQrCodeInfo(qrCodeInfo);
            ServletUtil.writeJson(response, R.ok().msg("用户授权成功").build());
        } catch (Exception e) {
            log.error("二维码登录授权异常 {}", e.getMessage(), e);
            ServletUtil.writeJson(response, R.error().msg(e.getMessage()).build());
        }
    }

}