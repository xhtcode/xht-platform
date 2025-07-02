package com.xht.framework.core.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * feign 基础配置
 *
 * @author xht
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasicFeignProperties {

    /**
     * 服务名称唯一
     */
    private String contextId;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 链接地址
     */
    private String url;
}
