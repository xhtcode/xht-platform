package com.xht.framework.core.enums;

/**
 * 调用类型枚举
 *
 * @author xht
 */
public enum CallTypeEnums {
    /**
     * 本地调用方式
     */
    LOCAL,
    /**
     * 数据库连接调用方式
     */
    JDBC,
    /**
     * 远程服务调用方式
     */
    FEIGN,
    /**
     * MQ消息队列调用方式
     */
    MQ;

}