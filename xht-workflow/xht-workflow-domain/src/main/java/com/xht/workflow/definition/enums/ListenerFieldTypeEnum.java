package com.xht.workflow.definition.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.common.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述： 监听字段类型 字符串、表达式
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum ListenerFieldTypeEnum implements XhtEnum<String> {

    /**
     * 字符串
     */
    STRING("string", "字符串"),

    /**
     * 表达式
     */
    EXPRESSION("expression", "表达式");;

    /**
     * 监听字段类型
     */
    @JsonValue
    private final String value;

    /**
     * 描述
     */
    private final String desc;
}
