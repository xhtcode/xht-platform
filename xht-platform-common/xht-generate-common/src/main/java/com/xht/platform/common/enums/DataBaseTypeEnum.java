package com.xht.platform.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.common.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据库类型枚举
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum DataBaseTypeEnum implements XhtEnum<String> {

    MYSQL("MySql", "com.mysql.cj.jdbc.Driver"),

    ORACLE("Oracle", "oracle.jdbc.OracleDriver"),

    ;

    /**
     * 数据库类型
     */
    @JsonValue
    private final String value;

    /**
     * 数据库驱动
     */
    private final String driverClassName;

    @Override
    public String toString() {
        return value;
    }
}