package com.xht.framework.core.properties;

import com.xht.framework.core.properties.basic.EnableProperties;
import com.xht.framework.core.properties.log.BLogProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Optional;

/**
 * 应用配置
 *
 * @author xht
 **/
@Data
@ConfigurationProperties(prefix = "xht")
public class XhtConfigProperties {

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
     * 判断是否启用横幅功能
     * <p>
     * 该方法通过全局配置对象检查横幅功能是否启用，如果配置对象为空或未明确设置，
     * 则默认返回true表示启用横幅功能
     *
     * @return boolean 如果横幅功能启用则返回true，否则返回false
     */
    public boolean isBanner() {
        return Optional.ofNullable(global)
                .map(GlobalConfigProperties::getBanner)
                .map(EnableProperties::isEnable)
                .orElse(true);
    }


}
