package com.xht.modules.common.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据库类型枚举
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum DataBaseTypeEnums implements IEnum<String> {

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