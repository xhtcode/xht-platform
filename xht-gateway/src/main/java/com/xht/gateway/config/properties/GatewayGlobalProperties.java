package com.xht.gateway.config.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @author xht
 **/
@Data
@NoArgsConstructor
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "xht.gateway.global")
public class GatewayGlobalProperties {

    /**
     * 时间戳有效期（秒）
     */
    private long timestampValidPeriod = 60 * 2;


}
