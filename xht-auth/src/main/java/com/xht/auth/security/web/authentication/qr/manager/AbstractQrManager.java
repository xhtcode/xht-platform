package com.xht.auth.security.web.authentication.qr.manager;

import com.xht.auth.security.web.authentication.qr.QrCodeProperties;
import com.xht.auth.security.web.authentication.qr.domain.QrCodeInfo;
import com.xht.auth.security.web.authentication.qr.exception.QyLoginException;
import com.xht.framework.security.core.userdetails.BasicUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.savedrequest.RequestCache;

import java.util.Optional;

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

    /**
     * 生成二维码票据
     *
     * @return 二维码票据字符串
     */
    public abstract String generateQrTicket();

    /**
     * 生成二维码ID并初始化二维码信息
     * <p>
     * 该方法会创建一个待扫描状态的二维码，保存请求缓存中的原始请求信息，
     * 并将二维码信息存储到Redis中。
     * </p>
     *
     * @param requestCache 请求缓存，用于获取登录前保存的请求信息
     * @param request HTTP请求对象
     * @param response HTTP响应对象
     * @return 包含二维码ID、状态和过期时间等信息的QrCodeInfo对象
     */
    public abstract QrCodeInfo generateQrId(RequestCache requestCache, HttpServletRequest request, HttpServletResponse response);

    /**
     * 根据二维码ID获取二维码信息
     *
     * @param qrCodeId 二维码ID
     * @return 包含QrCodeInfo的Optional对象，不存在时返回empty
     */
    public abstract Optional<QrCodeInfo> getQrCodeInfo(String qrCodeId);

    /**
     * 更新二维码信息
     * <p>
     * 会对输入参数进行校验，并将更新后的二维码信息保存到Redis中。
     * </p>
     *
     * @param qrCodeInfo 要更新的二维码信息对象
     * @throws IllegalArgumentException 当二维码信息为空或二维码ID为空时抛出异常
     */
    public abstract void updateQrCodeInfo(QrCodeInfo qrCodeInfo);

    /**
     * 保存用户数据到二维码关联的缓存中
     * <p>
     * 仅在二维码状态为已确认(CONFIRMED)时允许保存用户数据，
     * 用户数据在Redis中存储1分钟。
     * </p>
     *
     * @param qrCodeInfo 二维码信息对象
     * @param basicUserDetails 用户详细信息
     * @throws IllegalArgumentException 当二维码信息、二维码ID或用户信息为空时抛出异常
     * @throws IllegalStateException 当二维码状态不是已确认状态时抛出异常
     */
    public abstract void saveUserData(QrCodeInfo qrCodeInfo, BasicUserDetails basicUserDetails);

    /**
     * 移除二维码相关信息
     * <p>
     * 删除Redis中存储的二维码票据和用户信息数据。
     * </p>
     *
     * @param qrCodeInfo 二维码信息对象
     * @throws IllegalArgumentException 当二维码信息、二维码ID或二维码票据为空时抛出异常
     */
    public abstract void removeQrInfo(QrCodeInfo qrCodeInfo);

    /**
     * 获取二维码关联的用户数据
     * <p>
     * 仅在二维码状态为已确认(CONFIRMED)时允许获取用户数据。
     * </p>
     *
     * @param qrCodeInfo 二维码信息对象
     * @return 用户详细信息对象
     * @throws IllegalArgumentException 当二维码信息或二维码ID为空时抛出异常
     * @throws IllegalStateException 当二维码状态不是已确认状态时抛出异常
     */
    public abstract BasicUserDetails getUserData(QrCodeInfo qrCodeInfo);
}
