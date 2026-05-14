package com.xht.auth.security.web.authentication.qr.domain;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 二维码票据信息
 *
 * @author xht
 **/
@Data
public class QrTicketInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = -1L;

    /**
     * 二维码id
     */
    private String qrCodeId;

    /**
     * 二维码票据
     */
    private String qrTicket;


}
