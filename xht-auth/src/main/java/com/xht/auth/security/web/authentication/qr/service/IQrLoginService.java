package com.xht.auth.security.web.authentication.qr.service;

import com.xht.auth.security.web.authentication.qr.domain.request.QrCodeLoginConsentRequest;
import com.xht.auth.security.web.authentication.qr.domain.request.QrCodeLoginScanRequest;
import com.xht.auth.security.web.authentication.qr.domain.response.QrCodeGenerateResponse;
import com.xht.auth.security.web.authentication.qr.domain.response.QrCodeLoginFetchResponse;
import com.xht.auth.security.web.authentication.qr.domain.response.QrCodeLoginScanResponse;
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

    /**
     * 处理二维码扫描请求
     * 用户扫描二维码后调用此方法，记录扫描状态并返回登录确认所需的信息
     *
     * @param loginScan 二维码扫描请求对象，包含二维码ID等扫描信息
     * @return 二维码扫描响应对象，包含扫描结果和后续操作指引
     */
    QrCodeLoginScanResponse scan(QrCodeLoginScanRequest loginScan);

    /**
     * 处理二维码登录授权确认
     * 用户在移动端确认登录后调用此方法，完成登录授权流程
     *
     * @param loginConsent 二维码登录授权请求对象，包含用户授权信息
     */
    void consent(QrCodeLoginConsentRequest loginConsent);

    /**
     * 获取二维码登录状态和结果
     * 客户端轮询此接口以获取二维码的当前状态，包括是否已扫描、是否已授权以及最终的登录凭证
     *
     * @param request HTTP请求对象，用于获取客户端信息
     * @param qrCodeId 二维码ID，唯一标识一个二维码
     * @return 二维码登录获取响应对象，包含登录状态和认证结果
     */
    QrCodeLoginFetchResponse fetch(HttpServletRequest request, HttpServletResponse response, String qrCodeId);

}