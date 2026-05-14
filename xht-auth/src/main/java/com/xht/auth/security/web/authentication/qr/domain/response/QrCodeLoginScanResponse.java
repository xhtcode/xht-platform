package com.xht.auth.security.web.authentication.qr.domain.response;

import lombok.Data;

import java.util.Set;

/**
 * 扫描二维码响应bean
 *
 * @author xht
 */
@Data
public class QrCodeLoginScanResponse {

    /**
     * 扫描临时票据
     */
    private String qrCodeTicket;

    /**
     * 待确认scope
     */
    private Set<String> scopes;

}