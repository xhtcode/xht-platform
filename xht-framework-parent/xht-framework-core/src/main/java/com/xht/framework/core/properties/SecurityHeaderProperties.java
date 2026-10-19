package com.xht.framework.core.properties;

import com.xht.framework.utils.AssertUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.xht.framework.common.constant.HttpConstants.Header.AUTH_KEY;

/**
 * 描述 ：自定义 OpenFeign 配置
 *
 * @author xht
 **/
@Data
@ConfigurationProperties(prefix = "xht.security")
public class SecurityHeaderProperties {

    private Header header = new Header();

    public String getAuthKey() {
        AssertUtils.notNull(header, "查询不到具体请求头配置");
        return header.getAuthKey();
    }

    public String getAuthValue() {
        AssertUtils.notNull(header, "查询不到具体请求头配置");
        return header.getAuthValue();
    }

    @Data
    public static class Header {

        private String authKey = AUTH_KEY.getValue();

        /**
         * 授权请求头 值
         */
        private String authValue;

    }

}
