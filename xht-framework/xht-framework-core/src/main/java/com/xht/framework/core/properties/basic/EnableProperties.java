package com.xht.framework.core.properties.basic;

import com.xht.framework.core.properties.IProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 通用配置类
 *
 * @author xht
 **/
@Data
public class EnableProperties implements IProperties {

    /**
     * 是否开启，默认为 true
     */
    private boolean enable;

    public EnableProperties() {
        this.enable = false;
    }

    public EnableProperties(boolean enable) {
        this.enable = enable;
    }

}
