package com.xht.framework.core.domain.properties;

import lombok.Data;

/**
 * 通用配置类
 *
 * @author xht
 **/
@Data
public abstract class CommonProperties {

    /**
     * 是否开启，默认为 true
     */
    private boolean enable = true;
}
