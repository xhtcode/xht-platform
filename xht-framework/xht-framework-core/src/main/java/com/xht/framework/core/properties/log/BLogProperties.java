package com.xht.framework.core.properties.log;

import lombok.Data;

/**
 * 日志配置
 *
 * @author xht
 **/
@Data
public class BLogProperties {

    /**
     * 日志存储类型
     */
    private BLogRepositoryType repositoryType = BLogRepositoryType.JDBC;

    /**
     * 日志存储类型
     */
    public enum BLogRepositoryType {
        JDBC,
        FEIGN
    }
}
