package com.xht.framework.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 小糊涂项目启动公共属性
 *
 * @author xht
 **/
@Data
@Component
@ConfigurationProperties(prefix = "xht.global-config")
public class GlobalConfigProperties {

    /**
     * 是否开启 banner LOGO
     */
    private boolean banner = true;

}
