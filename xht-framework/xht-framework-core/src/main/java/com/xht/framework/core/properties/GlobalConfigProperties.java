package com.xht.framework.core.properties;

import com.xht.framework.core.properties.basic.EnableProperties;
import lombok.Data;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * 小糊涂项目启动公共属性
 *
 * @author xht
 **/
@Data
public class GlobalConfigProperties {

    @NestedConfigurationProperty
    private EnableProperties banner = new EnableProperties(true);

}
