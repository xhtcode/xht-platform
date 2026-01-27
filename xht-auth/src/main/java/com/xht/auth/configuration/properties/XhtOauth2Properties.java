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
     * 客户端属性
     */
    @Data
    public static class Client {
        /**
         * 缓存超时时间
         */
        private Long timeout = 60L * 3;
    }

}
