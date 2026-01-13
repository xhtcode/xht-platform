package com.xht.auth.configuration.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Oauth2属性
 *
 * @author xht
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "xht.security.oauth2")
public class XhtOauth2Properties {


    /**
     * 认证服务器地址
     */
    @Value("${xht.security.oauth2.issuer:http://127.0.0.1:${server.port:8080}}")
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

        private Long timeout = 60L;

    }

    public static class Authorization {
    }

}
