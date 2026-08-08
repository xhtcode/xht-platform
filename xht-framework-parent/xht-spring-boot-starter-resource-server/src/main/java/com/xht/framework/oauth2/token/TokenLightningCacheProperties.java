package com.xht.framework.oauth2.token;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * token 配置
 *
 * @author xht
 **/
@Data
@ConfigurationProperties(prefix = "xht.security.token.lightning-cache")
public class TokenLightningCacheProperties {

    /**
     * 缓存令牌有效期 默认 5 分钟
     */
    private long expired = 60 * 5;


}
