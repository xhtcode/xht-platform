package com.xht.framework.core.properties;

import com.xht.framework.core.properties.basic.EnableProperties;
import com.xht.framework.core.properties.basic.MessageProperties;
import com.xht.framework.core.properties.cache.CacheProperties;
import com.xht.framework.core.properties.log.BLogProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * 应用配置
 *
 * @author xht
 **/
@Data
@ConfigurationProperties(prefix = "xht")
public class XhtConfigProperties implements IProperties {

    /**
     * 全局配置
     */
    @NestedConfigurationProperty
    private GlobalConfigProperties global;

    /**
     * blog 日志配置
     */
    @NestedConfigurationProperty
    private BLogProperties blog;

    /**
     * 消息配置
     */
    @NestedConfigurationProperty
    private MessageProperties message;

    /**
     * 小糊涂项目启动公共属性
     *
     * @author xht
     **/
    @Data
    public static class GlobalConfigProperties {

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

}
