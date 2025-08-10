package com.xht.generate.constant;

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

    MYSQL("MySql", "MySQL数据库"),

    ORACLE("Oracle", "Oracle数据库"),

    ;

    /**
     * 数据库类型
     */
    @JsonValue
    private final String value;

    /**
     * 数据库描述
     */
    private final String desc;
}