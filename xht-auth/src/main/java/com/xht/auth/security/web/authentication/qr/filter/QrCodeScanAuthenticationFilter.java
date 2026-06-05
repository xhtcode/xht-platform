package com.xht.auth.security.web.authentication.qr.filter;

import com.xht.auth.security.web.authentication.qr.domain.QrCodeInfo;
import com.xht.auth.security.web.authentication.qr.domain.response.QrCodeLoginScanResponse;
import com.xht.auth.security.web.authentication.qr.enums.QrCodeStatusEnums;
import com.xht.auth.security.web.authentication.qr.exception.QyLoginException;
import com.xht.framework.core.domain.R;
import com.xht.framework.core.utils.ServletUtil;
import com.xht.framework.oauth2.utils.SecurityUtils;
import com.xht.framework.security.core.userdetails.BasicUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 二维码扫码登录过滤器
 * @author xht
 */
@Slf4j
public class QrCodeScanAuthenticationFilter extends QrCodeAbstractAuthenticationFilter {

    public QrCodeScanAuthenticationFilter(String defaultFilterProcessesUrl) {
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
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
        String qrCodeId = request.getParameter("qrCodeId");
        BasicUserDetails basicUser = SecurityUtils.getUser();
        log.debug("用户扫描二维码 qrCodeId:{} username={}", qrCodeId, basicUser.getUsername());
        try {
            QrCodeInfo info = qrDataManager.getQrCodeInfo(qrCodeId).orElseThrow(() -> new QyLoginException("无效二维码."));
            // 验证状态
            if (!Objects.equals(info.getQrCodeStatus(), QrCodeStatusEnums.WAITING)) {
                throw new QyLoginException("二维码已被其他人扫描，无法重复扫描.");
            }
            QrCodeLoginScanResponse loginScanResponse = new QrCodeLoginScanResponse();
            // 生成临时票据
            String qrCodeTicket = qrDataManager.generateQrTicket();
            log.info("用户[{}]扫码登录,二维码ID:{} 生成二维码临时票据:{}", basicUser.getUsername(), qrCodeId, qrCodeTicket);
            // 更新二维码信息的状态
            info.setName(basicUser.getName());
            info.setAvatarUrl(basicUser.getNickName());
            info.setQrCodeTicket(qrCodeTicket);
            info.setQrCodeStatus(QrCodeStatusEnums.SCANNED);
            qrDataManager.updateQrCodeInfo(info);
            // 封装响应
            loginScanResponse.setQrCodeTicket(qrCodeTicket);
            loginScanResponse.setScopes(info.getScopes());
            ServletUtil.writeJson(response, R.ok().build(loginScanResponse));
        } catch (Exception e) {
            log.error("用户[{}]扫码登录异常:{}", basicUser.getUsername(), e.getMessage());
            ServletUtil.writeJson(response, R.error().msg(e.getMessage()).build());
        }
    }

}