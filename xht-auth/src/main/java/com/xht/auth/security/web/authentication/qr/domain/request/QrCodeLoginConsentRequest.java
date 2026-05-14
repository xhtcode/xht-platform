package com.xht.auth.security.web.authentication.qr.domain.request;

import lombok.Data;

/**
 * 二维码登录确认入参
 *
 * @author xht
 */
@Data
public class QrCodeLoginConsentRequest {

    /**
     * 是否同意
     */
    private Boolean consent;

    /**
     * 二维码id
     */
    private String qrCodeId;

    /**
     * 扫码二维码后产生的临时票据(仅一次有效)
     */
    private String qrCodeTicket;

}