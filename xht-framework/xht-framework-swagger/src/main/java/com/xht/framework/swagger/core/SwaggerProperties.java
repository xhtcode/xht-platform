package com.xht.framework.swagger.core;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 描述 ： 自定义swagger 配置类
 *
 * @author xht
 **/
@Data
@ConfigurationProperties(value = "xht.cloud.swagger")
public class SwaggerProperties {

    /**
     * 标题
     */
    private String title = "xht微服务-接口文档";
    /**
     * 描述
     */
    private String description = "xht微服务平台";
    /**
     * 作者
     */
    private String author = "xht";
    /**
     * 版本
     */
    private String version = "v1";
    /**
     * url
     */
    private String url = "https://xhtcode.github.io/xht-cloud-doc/";
    /**
     * email
     */
    private String email = "616326125@qq.com";

}
