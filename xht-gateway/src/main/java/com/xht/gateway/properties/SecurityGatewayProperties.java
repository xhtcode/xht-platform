package com.xht.gateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

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


}
