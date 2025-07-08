package com.xht.framework.security.properties;

import com.xht.framework.core.properties.IProperties;
import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * token 配置
 *
 * @author xht
 **/
@Data
@ConfigurationProperties(prefix = "xht.security.token")
public class TokenProperties  implements IProperties {

    /**
     * 刷新令牌有效期默认 30 天
     */

    private TokenValidity refresh = new TokenValidity(60 * 60 * 24 * 30);

    /**
     * 请求令牌有效期默认 12 小时
     */
    private TokenValidity access = new TokenValidity(60 * 60 * 12);

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static class TokenValidity {

        int validitySeconds;

    }
}
