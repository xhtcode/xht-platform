package com.xht.auth.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Oauth2属性
 *
 * @author xht
 **/
@Data
@Component
@ConfigurationProperties(prefix = "xht.security.oauth2")
public class XhtOauth2Properties {

    /**
     * 认证服务器地址
     */
    private String issuer;

    /**
     * 客户端属性
     */
    private Client client = new Client();

    /**
     * 授权服务器
     */
    private final AuthorizationServer authorizationServer = new AuthorizationServer();

    /**
     * 客户端属性
     */
    @Data
    public static class Client {
        /**
         * 缓存超时时间
         */
        private Long timeout = 60L * 3;
    }



    @Data
    public static class AuthorizationServer {

        /**
         * 退出登录
         */
        private String logoutUrl;

        /**
         * 表单登录地址
         */
        private String formLoginUrl;

        /**
         * 手机号登录地址
         */
        private String phoneLoginUrl;

        /**
         *如果需要进行身份验证，则会跳转至该登录页面。
         */
        private String loginPage;

        /**
         * 授权页面
         */
        private String consentPage;

    }

}
