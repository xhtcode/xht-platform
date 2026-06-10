package com.xht.auth.security.web.authentication.qr;

import com.xht.auth.security.web.authentication.qr.filter.QrCodeConsentAuthenticationFilter;
import com.xht.auth.security.web.authentication.qr.filter.QrCodeQueryAuthenticationFilter;
import com.xht.auth.security.web.authentication.qr.filter.QrCodeScanAuthenticationFilter;
import com.xht.auth.security.web.authentication.qr.manager.AbstractQrManager;
import com.xht.framework.exception.utils.ThrowUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

/**
 * 扫码登录配置
 *
 * @author xht
 */
@Slf4j
@Setter
public class XhtQrCodeLoginConfigurer extends AbstractHttpConfigurer<XhtQrCodeLoginConfigurer, HttpSecurity> {

    protected AbstractQrManager qrDataManager;

    protected AuthenticationSuccessHandler successHandler;

    protected AuthenticationFailureHandler failureHandler;

    @Override
    public void init(HttpSecurity http) {
        ThrowUtils.notNull(this.qrDataManager, "qrDataManager cannot be null");
        ThrowUtils.notNull(this.successHandler, "successHandler cannot be null");
        ThrowUtils.notNull(this.failureHandler, "failureHandler cannot be null");
        QrCodeScanAuthenticationFilter scanAuthenticationFilter = new QrCodeScanAuthenticationFilter("/qrcode/scan");
        scanAuthenticationFilter.setQrDataManager(this.qrDataManager);
        http.addFilterAfter(scanAuthenticationFilter, BearerTokenAuthenticationFilter.class);
        QrCodeConsentAuthenticationFilter consentAuthenticationFilter = new QrCodeConsentAuthenticationFilter("/qrcode/constant");
        consentAuthenticationFilter.setQrDataManager(this.qrDataManager);
        http.addFilterAfter(consentAuthenticationFilter, BearerTokenAuthenticationFilter.class);
    }

    @Override
    public void configure(HttpSecurity http) {
        QrCodeQueryAuthenticationFilter qrCodeQueryAuthenticationFilter = new QrCodeQueryAuthenticationFilter("/qrcode/query");
        qrCodeQueryAuthenticationFilter.setQrDataManager(this.qrDataManager);
        qrCodeQueryAuthenticationFilter.setSuccessHandler(this.successHandler);
        qrCodeQueryAuthenticationFilter.setFailureHandler(this.failureHandler);
        RememberMeServices rememberMeServices = http.getSharedObject(RememberMeServices.class);
        qrCodeQueryAuthenticationFilter.setRememberMeServices(rememberMeServices);
        SessionAuthenticationStrategy sessionAuthenticationStrategy = http.getSharedObject(SessionAuthenticationStrategy.class);
        qrCodeQueryAuthenticationFilter.setSessionStrategy(sessionAuthenticationStrategy);
        http.addFilterBefore(qrCodeQueryAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }


    public static XhtQrCodeLoginConfigurer qrCodeLogin() {
        return new XhtQrCodeLoginConfigurer();
    }

}