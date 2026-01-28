package com.xht.framework.core.properties;

import com.xht.framework.core.exception.utils.ThrowUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.xht.framework.core.constant.HttpConstants.Header.AUTH_KEY;

/**
 * 描述 ：自定义 OpenFeign 配置
 *
 * @author xht
 **/
@Data
@ConfigurationProperties(prefix = "xht.cloud.feign")
public class SecurityHeaderProperties implements IProperties {

    private Header header = new Header();

    public String getAuthKey() {
        ThrowUtils.notNull(header, "查询不到具体请求头配置");
        return header.getAuthKey();
    }

    public String getAuthValue() {
        ThrowUtils.notNull(header, "查询不到具体请求头配置");
        return header.getAuthValue();
    }

    @Data
    static class Header {

        private String authKey = AUTH_KEY.getValue();

        /**
         * 授权请求头 值
         */
        private String authValue = "123456";

    }

}
