package com.xht.framework.security.properties;

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
@ConfigurationProperties(prefix = "xht.oauth2.grant-type")
public class Oauth2GrantTypeProperties {

    /**
     * 授权类型
     */
    private String value;

}
