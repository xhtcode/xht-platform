package com.xht.framework.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据类型枚举
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum DataTypeEnum implements XhtEnum<Integer> {

    /**
     * 普通数据
     */
    NORMAL(0, "普通数据"),

    /**
     * 加密数据
     */
    ENCRYPT(1, "加密数据"),
    ;

    @JsonValue
    private final Integer value;

    private final String desc;

}