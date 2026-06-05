package com.xht.auth.security.web.authentication.qr.service.impl;

import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.xht.auth.security.web.authentication.qr.domain.QrCodeInfo;
import com.xht.auth.security.web.authentication.qr.domain.response.QrCodeGenerateResponse;
import com.xht.auth.security.web.authentication.qr.manager.QrDataManager;
import com.xht.auth.security.web.authentication.qr.service.IQrLoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Service;

/**
 * 二维码登录接口实现
 *
 * @author xht
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class QrLoginServiceImpl implements IQrLoginService {

    private final QrDataManager qrDataManager;

    private final RequestCache requestCache = new HttpSessionRequestCache();

    /**
     * 生成二维码登录码
     * 创建一个新的二维码，包含唯一的二维码ID，用于后续的扫码登录流程
     *
     * @return 二维码生成响应对象，包含二维码ID和相关信息
     */
    @Override
    public QrCodeGenerateResponse generateQrCode(HttpServletRequest request, HttpServletResponse response) {
        // 生成二维码唯一id
        QrCodeInfo qrCodeInfo = qrDataManager.generateQrId(requestCache, request, response);
        String qrCodeId = qrCodeInfo.getQrCodeId();
        // 生成二维码并转为base64
        String pngQrCode = QrCodeUtil.generateAsBase64(qrCodeId, new QrConfig(), "png");
        return new QrCodeGenerateResponse(qrCodeId, pngQrCode);
    }

}