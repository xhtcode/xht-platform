package com.xht.generate.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据库类型枚举
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum DataBaseTypeEnums {

    MYSQL("MySQL", "MySQL数据库"),

    ORACLE("Oracle", "Oracle数据库"),

    ;

    /**
     * 数据库类型
     */
    private final String value;

    /**
     * 数据库描述
     */
    private final String desc;
}