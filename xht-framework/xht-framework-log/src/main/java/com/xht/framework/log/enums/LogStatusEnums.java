package com.xht.framework.log.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 日志类型枚举
 *
 * @author xht
 */
@Getter
@RequiredArgsConstructor
public enum LogStatusEnums implements IEnum<Integer> {

    /**
     * 正常日志类型
     */
    NORMAL(0, "正常日志"),

    /**
     * 错误日志类型
     */
    ERROR(1, "错误日志");

    /**
     * 类型
     */
    @JsonValue
    private final Integer value;

    /**
     * 描述
     */
    private final String description;

}