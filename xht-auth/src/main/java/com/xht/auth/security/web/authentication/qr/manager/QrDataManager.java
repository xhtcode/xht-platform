package com.xht.auth.security.web.authentication.qr.manager;

import com.xht.auth.security.web.authentication.qr.domain.QrCodeInfo;
import com.xht.auth.security.web.authentication.qr.enums.QrCodeStatusEnums;
import com.xht.framework.cache.repository.RedisRepository;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.core.utils.IdUtils;
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

    public String generateQrTicket() {
        return IdUtils.simpleUUID();
    }

    /**
     * 生成二维码唯一标识ID
     * 使用UUID算法生成简洁格式的二维码标识符，用于唯一标识一个二维码
     *
     * @return 生成的二维码ID，格式为不带连字符的UUID字符串
     */
    public QrCodeInfo generateQrId(RequestCache requestCache, HttpServletRequest request, HttpServletResponse response) {
        String qrCodeId = IdUtils.simpleUUID();
        QrCodeInfo qrCodeInfo = QrCodeInfo.builder().qrCodeId(qrCodeId)
                // 待扫描状态
                .qrCodeStatus(QrCodeStatusEnums.WAIT_SCAN)
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


    public Optional<QrCodeInfo> getQrCodeInfo(String qrCodeId) {
        return Optional.ofNullable(redisRepository.get(buildQrDataKey(qrCodeId)));
    }

    public void updateQrCodeInfo(QrCodeInfo qrCodeInfo) {
        ThrowUtils.notNull(qrCodeInfo, "二维码信息不能为空.");
        ThrowUtils.hasText(qrCodeInfo.getQrCodeId(), "二维码ID不能为空.");
        redisRepository.set(buildQrDataKey(qrCodeInfo.getQrCodeId()), qrCodeInfo, qrCodeProperties.getTimeout(), TimeUnit.SECONDS);
    }

    public void saveUserData(QrCodeInfo qrCodeInfo, BasicUserDetails basicUserDetails) {
        ThrowUtils.notNull(qrCodeInfo, "二维码信息不能为空.");
        ThrowUtils.hasText(qrCodeInfo.getQrCodeId(), "二维码ID不能为空.");
        ThrowUtils.notNull(basicUserDetails, "用户信息不能为空.");
        ThrowUtils.throwIf(!Objects.equals(qrCodeInfo.getQrCodeStatus(), QrCodeStatusEnums.CONFIRMED), "用户未授权信息.");
        redisRepository.set(buildQrUserinfoKey(qrCodeInfo.getQrCodeId()), basicUserDetails, 1, TimeUnit.MINUTES);
    }

    public void removeQrInfo(QrCodeInfo qrCodeInfo) {
        ThrowUtils.notNull(qrCodeInfo, "二维码信息不能为空.");
        ThrowUtils.hasText(qrCodeInfo.getQrCodeId(), "二维码ID不能为空.");
        ThrowUtils.hasText(qrCodeInfo.getQrCodeTicket(), "二维码票据不能为空.");
        redisRepository.delete(buildQrTicketKey(qrCodeInfo.getQrCodeId(), qrCodeInfo.getQrCodeTicket()));
        redisRepository.delete(buildQrUserinfoKey(qrCodeInfo.getQrCodeId()));
    }

    public BasicUserDetails getUserData(QrCodeInfo qrCodeInfo) {
        ThrowUtils.notNull(qrCodeInfo, "二维码信息不能为空.");
        ThrowUtils.hasText(qrCodeInfo.getQrCodeId(), "二维码ID不能为空.");
        ThrowUtils.throwIf(!Objects.equals(qrCodeInfo.getQrCodeStatus(), QrCodeStatusEnums.CONFIRMED), "用户未授权信息.");
        return redisRepository.get(buildQrUserinfoKey(qrCodeInfo.getQrCodeId()));
    }
}
