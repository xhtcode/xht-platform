package com.xht.framework.core.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性别枚举
 */
@Getter
@AllArgsConstructor
public enum GenderEnums implements IEnum<Integer> {

    MALE(0, "男"),

    FEMALE(1, "女"),

    UNKNOWN(99, "未知"),
    ;

    /**
     * 枚举值
     */
    @JsonValue
    private final Integer value;

    private final String desc;

}