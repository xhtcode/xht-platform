package com.xht.generate.constant;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * 语言类型枚举
 * 包含常见编程语言的类型定义
 */
@Getter
@AllArgsConstructor
public enum LanguageTypeEnums implements IEnum<String> {

    Java("Java"),

    Python("Python"),

    JavaScript("JavaScript"),

    TypeScript("TypeScript"),

    Vue("Vue");

    /**
     * 语言类型编码
     */
    @JsonValue
    private final String value;

    /**
     * 根据编码获取枚举
     *
     * @param code 语言类型编码
     * @return 对应的枚举实例，不存在则返回空
     */
    public static Optional<LanguageTypeEnums> getByCode(String code) {
        return Arrays.stream(values())
                .filter(enums -> enums.getValue().equalsIgnoreCase(code))
                .findFirst();
    }
}
    