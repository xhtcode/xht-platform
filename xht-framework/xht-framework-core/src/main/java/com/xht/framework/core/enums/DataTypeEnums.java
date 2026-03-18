package com.xht.framework.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据类型枚举
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum DataTypeEnums implements XhtEnum<Integer> {

    /**
     * 普通数据
     */
    NORMAL(1, "普通数据"),

    /**
     * 加密数据
     */
    ENCRYPT(10, "加密数据"),
    ;
    private final Integer value;

    private final String desc;

}