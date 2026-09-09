package com.xht.framework.core.properties;

import com.xht.framework.core.properties.basic.EnableProperties;
import com.xht.framework.core.properties.cache.CacheProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * 应用配置
 *
 * @author xht
 **/
@Data
@ConfigurationProperties(prefix = "xht.global")
public class XhtConfigProperties {

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 主机地址
     */
    private String hostName = "localhost";

    /**
     * 启动Banner属性
     */
    @NestedConfigurationProperty
    private EnableProperties banner = new EnableProperties(true);

    /**
     * 字典缓存配置属性
     */
    @NestedConfigurationProperty
    private CacheProperties dict = new CacheProperties();

}
