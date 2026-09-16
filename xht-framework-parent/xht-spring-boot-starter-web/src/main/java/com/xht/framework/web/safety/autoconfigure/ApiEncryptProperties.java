package com.xht.framework.web.safety.autoconfigure;

import com.xht.framework.core.properties.basic.EnableProperties;
import com.xht.framework.web.safety.ApiSafetyConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 描述： API解密相关配置
 *
 * @author xht
 **/
@Data
@Component
@ConfigurationProperties(prefix = ApiSafetyConstant.API_ENCRYPT_PROPERTIES_PREFIX)
public class ApiEncryptProperties extends EnableProperties {

    public ApiEncryptProperties() {
        super(Boolean.TRUE);
    }

    /**
     * 加密类型
     */
    private String encryptType = "BASE64";

}