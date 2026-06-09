package com.xht.gateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 网关安全配置属性
 *
 * @author xht
 **/
@Data
@Component
@ConfigurationProperties("xht.security.gateway")
public class SecurityGatewayProperties {

    /**
     * 需要认证的URL
     */
    private String[] authenticatedUrls;


    /**
     * 允许的跨域域名
     */
    private List<String> allowedOrigins;

    /**
     * 应用基础URI
     */
    private String appBaseUri;


}
