package com.xht.auth.security.web.authentication.qr.manager;

import com.xht.auth.security.web.authentication.qr.domain.QrCodeInfo;
import com.xht.auth.security.web.authentication.qr.enums.QrCodeStatusEnums;
import com.xht.framework.cache.repository.RedisRepository;
import com.xht.framework.exception.utils.ThrowUtils;
import com.xht.framework.utils.IdUtils;
import com.xht.framework.security.core.userdetails.BasicUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 二维码数据管理器
 *
 * @author xht
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class QrDataManager extends AbstractQrManager {

    private final RedisRepository redisRepository;

    /**
     * 生成二维码票据
     *
     * @return 二维码票据字符串
     */
    @Override
    public String generateQrTicket() {
        return IdUtils.simpleUUID();
    }

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
    @Override
    public QrCodeInfo generateQrId(RequestCache requestCache, HttpServletRequest request, HttpServletResponse response) {
        String qrCodeId = "1";
        QrCodeInfo qrCodeInfo = QrCodeInfo.builder().qrCodeId(qrCodeId)
                // 待扫描状态
                .qrCodeStatus(QrCodeStatusEnums.WAITING)
                // 1分钟后过期
                .expiresTime(LocalDateTime.now().plusMinutes(2L)).build();
        // 获取当前request
        // 获取当前session
        DefaultSavedRequest savedRequest = (DefaultSavedRequest) requestCache.getRequest(request, response);
        if (Objects.nonNull(savedRequest)) {
            // 获取查询参数与请求路径
            String queryString = savedRequest.getQueryString();
            String requestUri = savedRequest.getRequestURI();
            // 前后端不分离根据请求路径和请求参数跳转
            qrCodeInfo.setBeforeLoginRequestUri(requestUri);
            qrCodeInfo.setBeforeLoginQueryString(queryString);

            // 获取跳转登录之前访问url的query parameter
            String[] scopes = savedRequest.getParameterValues("scope");
            if (!ObjectUtils.isEmpty(scopes)) {
                // 不为空获取第一个并设置进二维码信息中
                qrCodeInfo.setScopes(Set.of(scopes[0].split(" ")));
            }
            // 前端可以根据scope显示要获取的信息，或固定显示要获取的信息
        }
        // 因为上边设置的过期时间是2分钟，这里设置10分钟过期，可根据业务自行调整过期时间
        redisRepository.set(buildQrDataKey(qrCodeId), qrCodeInfo, qrCodeProperties.getTimeout(), TimeUnit.SECONDS);
        return qrCodeInfo;
    }

    /**
     * 根据二维码ID获取二维码信息
     *
     * @param qrCodeId 二维码ID
     * @return 包含QrCodeInfo的Optional对象，不存在时返回empty
     */
    @Override
    public Optional<QrCodeInfo> getQrCodeInfo(String qrCodeId) {
        return Optional.ofNullable(redisRepository.get(buildQrDataKey(qrCodeId)));
    }

    /**
     * 更新二维码信息
     * <p>
     * 会对输入参数进行校验，并将更新后的二维码信息保存到Redis中。
     * </p>
     *
     * @param qrCodeInfo 要更新的二维码信息对象
     * @throws IllegalArgumentException 当二维码信息为空或二维码ID为空时抛出异常
     */
    @Override
    public void updateQrCodeInfo(QrCodeInfo qrCodeInfo) {
        ThrowUtils.notNull(qrCodeInfo, "二维码信息不能为空.");
        ThrowUtils.hasText(qrCodeInfo.getQrCodeId(), "二维码ID不能为空.");
        redisRepository.set(buildQrDataKey(qrCodeInfo.getQrCodeId()), qrCodeInfo, qrCodeProperties.getTimeout(), TimeUnit.SECONDS);
    }

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
    @Override
    public void saveUserData(QrCodeInfo qrCodeInfo, BasicUserDetails basicUserDetails) {
        ThrowUtils.notNull(qrCodeInfo, "二维码信息不能为空.");
        ThrowUtils.hasText(qrCodeInfo.getQrCodeId(), "二维码ID不能为空.");
        ThrowUtils.notNull(basicUserDetails, "用户信息不能为空.");
        ThrowUtils.throwIf(!Objects.equals(qrCodeInfo.getQrCodeStatus(), QrCodeStatusEnums.CONFIRMED), "用户未授权信息.");
        redisRepository.set(buildQrUserinfoKey(qrCodeInfo.getQrCodeId()), basicUserDetails, 1, TimeUnit.MINUTES);
    }

    /**
     * 移除二维码相关信息
     * <p>
     * 删除Redis中存储的二维码票据和用户信息数据。
     * </p>
     *
     * @param qrCodeInfo 二维码信息对象
     * @throws IllegalArgumentException 当二维码信息、二维码ID或二维码票据为空时抛出异常
     */
    @Override
    public void removeQrInfo(QrCodeInfo qrCodeInfo) {
        ThrowUtils.notNull(qrCodeInfo, "二维码信息不能为空.");
        ThrowUtils.hasText(qrCodeInfo.getQrCodeId(), "二维码ID不能为空.");
        ThrowUtils.hasText(qrCodeInfo.getQrCodeTicket(), "二维码票据不能为空.");
        redisRepository.delete(buildQrTicketKey(qrCodeInfo.getQrCodeId(), qrCodeInfo.getQrCodeTicket()));
        redisRepository.delete(buildQrUserinfoKey(qrCodeInfo.getQrCodeId()));
    }

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
    @Override
    public BasicUserDetails getUserData(QrCodeInfo qrCodeInfo) {
        ThrowUtils.notNull(qrCodeInfo, "二维码信息不能为空.");
        ThrowUtils.hasText(qrCodeInfo.getQrCodeId(), "二维码ID不能为空.");
        return redisRepository.get(buildQrUserinfoKey(qrCodeInfo.getQrCodeId()));
    }

}
