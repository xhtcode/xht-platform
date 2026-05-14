package com.xht.auth.security.web.authentication.qr.domain.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 扫描二维码入参
 *
 * @author xht
 */
@Data
public class QrCodeLoginScanRequest {

    /**
     * 二维码id
     */
    @NotEmpty(message = "二维码Id不能为空.")
    private String qrCodeId;

}