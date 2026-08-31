package com.xht.framework.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述： 默认状态枚举
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum DefaultStatusEnum implements XhtEnum<String> {

    /**
     * 非默认
     */
    NO("0", "非默认"),

    /**
     * 默认
     */
    YES("1", "默认"),

    /**
     * 未知
     */
    UNKNOWN("99", "未知");

    @JsonValue
    private final String value;

    private final String desc;

}
