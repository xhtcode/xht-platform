package com.xht.framework.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统内置的标志枚举
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum SystemFlagEnum implements XhtEnum<Integer> {

    /**
     * 0‑普通记录，非系统内置
     */
    NORMAL(0, "普通"),

    /**
     * 1‑系统内置记录，不可删除
     */
    SYSTEM(1, "系统内置");

    @JsonValue
    private final Integer value;

    private final String desc;

    /**
     * 根据value获取枚举
     */
    public static SystemFlagEnum getByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (SystemFlagEnum item : SystemFlagEnum.values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
    }

    /**
     * 判断是否为系统内置
     */
    public boolean isSystem() {
        return SYSTEM.equals(this);
    }

}