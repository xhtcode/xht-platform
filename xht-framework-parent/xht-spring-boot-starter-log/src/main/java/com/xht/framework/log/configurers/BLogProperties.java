package com.xht.framework.log.configurers;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 日志配置属性
 *
 * @author xht
 **/
@Data
@ConfigurationProperties("xht.blog")
public class BLogProperties {

    /**
     * 日志存储方式
     */
    private RepositoryType repositoryType = RepositoryType.DEFAULT;

    /**
     * 日志存储接口url
     */
    private String url;


    /**
     * 日志存储方式枚举
     */
    public enum RepositoryType {
        /**
         * 默认
         */
        DEFAULT,
        /**
         * 微服务模式
         */
        FEIGN,
        /**
         * JDBC
         */
        JDBC
    }

}
