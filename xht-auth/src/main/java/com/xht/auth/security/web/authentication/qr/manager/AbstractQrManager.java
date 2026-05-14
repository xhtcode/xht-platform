package com.xht.auth.security.web.authentication.qr.manager;

import com.xht.auth.security.web.authentication.qr.QrCodeProperties;
import com.xht.auth.security.web.authentication.qr.exception.QyLoginException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 抽象二维码管理器
 *
 * @author xht
 **/
@Slf4j
@Getter(value = AccessLevel.PROTECTED)
public abstract class AbstractQrManager {

    protected QrCodeProperties qrCodeProperties;

    @Autowired
    public void setQrCodeProperties(QrCodeProperties qrCodeProperties) {
        this.qrCodeProperties = qrCodeProperties;
    }

    /**
     * 构建二维码数据key
     * <p>根据二维码ID生成用于存储和检索二维码数据的缓存key</p>
     *
     * @param qrCodeId 二维码唯一标识，不能为空
     * @return 格式化后的缓存key
     * @throws QyLoginException 当qrCodeId为空时抛出
     */
    protected final String buildQrDataKey(String qrCodeId) {
        if (qrCodeId == null || qrCodeId.isEmpty()) {
            throw new QyLoginException("qrCodeId不能为空");
        }
        return qrCodeProperties.formatterKey(qrCodeId);
    }

    /**
     * 构建二维码票据key
     * <p>根据二维码ID和票据生成用于验证的缓存key</p>
     *
     * @param qrCodeId     二维码唯一标识，不能为空
     * @param qrCodeTicket 二维码票据，不能为空
     * @return 格式化后的缓存key
     * @throws QyLoginException 当qrCodeId或qrCodeTicket为空时抛出
     */
    protected final String buildQrTicketKey(String qrCodeId, String qrCodeTicket) {
        if (qrCodeId == null || qrCodeId.isEmpty()) {
            throw new QyLoginException("qrCodeId不能为空");
        }
        if (qrCodeTicket == null || qrCodeTicket.isEmpty()) {
            throw new QyLoginException("qrCodeTicket不能为空");
        }
        return qrCodeProperties.formatterKey(qrCodeId, qrCodeTicket);
    }

    /**
     * 构建用户信息key
     * @param qrCodeId 二维码唯一标识，不能为空
     * @return 缓存key
     */
    protected final String buildQrUserinfoKey(String qrCodeId) {
        return qrCodeProperties.formatterKey("userinfo", qrCodeId);
    }

}
