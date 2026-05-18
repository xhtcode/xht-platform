package com.xht.auth.security.web.authentication.qr.service;

import com.xht.auth.security.web.authentication.qr.domain.response.QrCodeGenerateResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 二维码登录服务接口
 *
 * @author xht
 */
public interface IQrLoginService {

    /**
     * 生成二维码登录码
     * 创建一个新的二维码，包含唯一的二维码ID，用于后续的扫码登录流程
     *
     * @return 二维码生成响应对象，包含二维码ID和相关信息
     */
    QrCodeGenerateResponse generateQrCode(HttpServletRequest request, HttpServletResponse response);

}