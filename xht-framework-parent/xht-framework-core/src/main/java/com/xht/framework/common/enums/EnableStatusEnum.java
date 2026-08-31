package com.xht.framework.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述： 启用状态枚举
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum EnableStatusEnum implements XhtEnum<String> {

    /**
     * 禁用
     */
    DISABLE("0", "禁用"),

    /**
     * 启用
     */
    ENABLE("1", "启用"),

    /**
     * 未知
     */
    UNKNOWN("99", "未知");

    @JsonValue
    private final String value;

    private final String desc;

}
