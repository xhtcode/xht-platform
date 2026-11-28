package com.xht.framework.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统内置标识枚举类
 * （0自定义 1系统内置，内置数据禁止删除）
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum SysBuiltinEnum implements XhtEnum<Integer> {

    /**
     * 自定义
     */
    CUSTOM(0, "自定义"),

    /**
     * 系统内置
     */
    BUILT_IN(1, "系统内置");

    @JsonValue
    private final Integer value;

    private final String desc;

    /**
     * 根据value获取枚举
     */
    public static SysBuiltinEnum getByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (SysBuiltinEnum item : SysBuiltinEnum.values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
    }

    /**
     * 判断是否为系统内置
     */
    public boolean isBuiltIn() {
        return BUILT_IN.equals(this);
    }

}
