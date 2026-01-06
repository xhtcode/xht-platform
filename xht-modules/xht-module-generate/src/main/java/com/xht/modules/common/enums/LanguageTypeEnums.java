package com.xht.modules.common.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 语言类型枚举
 * 包含常见编程语言的类型定义
 */
@Getter
@AllArgsConstructor
public enum LanguageTypeEnums implements IEnum<String> {

    Java("Java", "js", "Object"),
    TypeScript("TypeScript", "ts", "any");

    /**
     * 语言类型编码
     */
    @JsonValue
    private final String value;

    /**
     * 简称
     */
    private final String shortName;

    /**
     * 默认类型
     */
    private final String defaultType;

}
    