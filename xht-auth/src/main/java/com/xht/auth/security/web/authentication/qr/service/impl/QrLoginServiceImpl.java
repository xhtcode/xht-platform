package com.xht.auth.security.web.authentication.qr.service.impl;

import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.xht.auth.security.web.authentication.qr.domain.QrCodeInfo;
import com.xht.auth.security.web.authentication.qr.domain.request.QrCodeLoginConsentRequest;
import com.xht.auth.security.web.authentication.qr.domain.request.QrCodeLoginScanRequest;
import com.xht.auth.security.web.authentication.qr.domain.response.QrCodeGenerateResponse;
import com.xht.auth.security.web.authentication.qr.domain.response.QrCodeLoginFetchResponse;
import com.xht.auth.security.web.authentication.qr.domain.response.QrCodeLoginScanResponse;
import com.xht.auth.security.web.authentication.qr.enums.QrCodeStatusEnums;
import com.xht.auth.security.web.authentication.qr.exception.QyLoginException;
import com.xht.auth.security.web.authentication.qr.manager.QrDataManager;
import com.xht.auth.security.web.authentication.qr.service.IQrLoginService;
import com.xht.framework.oauth2.utils.SecurityUtils;
import com.xht.framework.security.core.userdetails.BasicUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

/**
 * 二维码登录接口实现
 *
 * @author xht
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class QrLoginServiceImpl implements IQrLoginService {

    private final QrDataManager qrDataManager;

    private final RequestCache requestCache = new HttpSessionRequestCache();

    /**
     * 生成二维码登录码
     * 创建一个新的二维码，包含唯一的二维码ID，用于后续的扫码登录流程
     *
     * @return 二维码生成响应对象，包含二维码ID和相关信息
     */
    @Override
    public QrCodeGenerateResponse generateQrCode(HttpServletRequest request, HttpServletResponse response) {
        // 生成二维码唯一id
        QrCodeInfo qrCodeInfo = qrDataManager.generateQrId(requestCache, request, response);
        String qrCodeId = qrCodeInfo.getQrCodeId();
        // 生成二维码并转为base64
        String pngQrCode = QrCodeUtil.generateAsBase64(qrCodeId, new QrConfig(), "png");
        return new QrCodeGenerateResponse(qrCodeId, pngQrCode);
    }

    /**
     * 处理二维码扫描请求
     * 用户扫描二维码后调用此方法，记录扫描状态并返回登录确认所需的信息
     *
     * @param loginScan 二维码扫描请求对象，包含二维码ID等扫描信息
     * @return 二维码扫描响应对象，包含扫描结果和后续操作指引
     */
    @Override
    public QrCodeLoginScanResponse scan(QrCodeLoginScanRequest loginScan) {
        BasicUserDetails basicUser = SecurityUtils.getUser();
        // 校验二维码状态
        QrCodeInfo info = qrDataManager.getQrCodeInfo(loginScan.getQrCodeId()).orElseThrow(() -> new QyLoginException("无效二维码."));
        // 验证状态
        if (!Objects.equals(info.getQrCodeStatus(), QrCodeStatusEnums.WAIT_SCAN)) {
            throw new QyLoginException("二维码已被其他人扫描，无法重复扫描.");
        }
        // 二维码是否过期
        boolean qrCodeExpire = info.getExpiresTime().isBefore(LocalDateTime.now());
        if (qrCodeExpire) {
            throw new QyLoginException("二维码已过期.");
        }
        QrCodeLoginScanResponse loginScanResponse = new QrCodeLoginScanResponse();
        // 生成临时票据
        String qrCodeTicket = qrDataManager.generateQrTicket();
        log.info("用户[{}]扫码登录,二维码ID:{} 生成二维码临时票据:{}", basicUser.getUsername(), loginScan.getQrCodeId(), qrCodeTicket);
        // 更新二维码信息的状态
        info.setName(basicUser.getName());
        info.setAvatarUrl(basicUser.getNickName());
        info.setQrCodeTicket(qrCodeTicket);
        info.setQrCodeStatus(QrCodeStatusEnums.SCANNED);
        qrDataManager.updateQrCodeInfo(info);
        // 封装响应
        loginScanResponse.setQrCodeTicket(qrCodeTicket);
        loginScanResponse.setScopes(info.getScopes());
        return loginScanResponse;
    }

    /**
     * 处理二维码登录授权确认
     * 用户在移动端确认登录后调用此方法，完成登录授权流程
     *
     * @param loginConsent 二维码登录授权请求对象，包含用户授权信息
     */
    @Override
    public void consent(QrCodeLoginConsentRequest loginConsent) {
        String qrCodeId = loginConsent.getQrCodeId();
        BasicUserDetails user = SecurityUtils.getUser();
        // 校验二维码状态
        QrCodeInfo qrCodeInfo = qrDataManager.getQrCodeInfo(qrCodeId).orElseThrow(() -> new QyLoginException("无效二维码或二维码已过期."));
        if (loginConsent.getConsent()) {
            qrDataManager.removeQrInfo(qrCodeInfo);
        }
        if (!Objects.equals(qrCodeInfo.getQrCodeStatus(), QrCodeStatusEnums.SCANNED)) {
            throw new QyLoginException("二维码已被其他人扫描，无法重复扫描.");
        }
        if (!Objects.equals(qrCodeInfo.getQrCodeTicket(), loginConsent.getQrCodeTicket())) {
            throw new QyLoginException("登录确认失败，请重新扫描二维码.");
        }
        if (!Objects.equals(qrCodeInfo.getName(), user.getName())) {
            throw new QyLoginException("非法请求，二维码盗刷");
        }
        // 更新二维码信息的状态
        qrCodeInfo.setQrCodeStatus(QrCodeStatusEnums.CONFIRMED);
        // 存储用户信息 更新二维码信息
        qrDataManager.saveUserData(qrCodeInfo, user);
        qrDataManager.updateQrCodeInfo(qrCodeInfo);
    }

    /**
     * 获取二维码登录状态和结果
     * 客户端轮询此接口以获取二维码的当前状态，包括是否已扫描、是否已授权以及最终的登录凭证
     *
     * @param request HTTP请求对象，用于获取客户端信息
     * @param qrCodeId 二维码ID，唯一标识一个二维码
     * @return 二维码登录获取响应对象，包含登录状态和认证结果
     */
    @Override
    public QrCodeLoginFetchResponse fetch(HttpServletRequest request, HttpServletResponse response, String qrCodeId) {
        // 校验二维码状态
        QrCodeInfo qrCodeInfo = qrDataManager.getQrCodeInfo(qrCodeId).orElseThrow(() -> new QyLoginException("无效二维码或二维码已过期."));
        // 二维码是否过期
        boolean qrCodeExpire = qrCodeInfo.getExpiresTime().isBefore(LocalDateTime.now());
        if (qrCodeExpire) {
            throw new QyLoginException("二维码已过期.");
        }
        QrCodeLoginFetchResponse loginFetchResponse = new QrCodeLoginFetchResponse();
        // 设置二维码是否过期、状态
        loginFetchResponse.setQrCodeStatus(qrCodeInfo.getQrCodeStatus());
        if (!Objects.equals(qrCodeInfo.getQrCodeStatus(), QrCodeStatusEnums.WAIT_SCAN)) {
            // 如果是已扫描/已确认
            loginFetchResponse.setName(qrCodeInfo.getName());
            loginFetchResponse.setAvatarUrl(qrCodeInfo.getAvatarUrl());
        }
        // 如果是已确认，将之前扫码确认的用户信息放入当前session中
        if (Objects.equals(qrCodeInfo.getQrCodeStatus(), QrCodeStatusEnums.CONFIRMED)) {
            // 根据二维码id从redis获取用户信息
            BasicUserDetails authenticationToken = qrDataManager.getUserData(qrCodeInfo);
            setLoginInfo(request, response, authenticationToken);
            // 操作成功后移除缓存
            qrDataManager.removeQrInfo(qrCodeInfo);
            // 填充二维码数据，设置跳转到登录之前的请求路径、查询参数和是否授权申请请求
            loginFetchResponse.setBeforeLoginRequestUri(qrCodeInfo.getBeforeLoginRequestUri());
            loginFetchResponse.setBeforeLoginQueryString(qrCodeInfo.getBeforeLoginQueryString());
        }
        return loginFetchResponse;
    }

    /**
     * 设置登录信息
     * @param request HTTP请求对象，用于获取当前session
     * @param authenticationToken 认证信息对象，包含用户信息
     */
    private void setLoginInfo(HttpServletRequest request, HttpServletResponse response, BasicUserDetails authenticationToken) {
        if (Objects.nonNull(authenticationToken)) {
            // 获取当前session
            HttpSession session = request.getSession(Boolean.FALSE);
            if (session != null) {
                // 获取到认证信息后将之前扫码确认的用户信息放入当前session中。
                UsernamePasswordAuthenticationToken passwordAuthentication = new UsernamePasswordAuthenticationToken(authenticationToken, null, authenticationToken.getAuthorities());
                session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, new SecurityContextImpl(passwordAuthentication));
            }
        } else {
            throw new QyLoginException("获取登录确认用户信息失败.");
        }
    }


}