package com.xht.framework.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 字符集常量枚举
 * 定义系统支持的字符编码集
 */
@Getter
@AllArgsConstructor
public enum CharacterEnums {

    /**
     * UTF-8字符集，Unicode的可变长度字符编码
     */
    UTF_8("UTF-8", "Unicode字符编码"),

    /**
     * GBK字符集，汉字内码扩展规范
     */
    GBK("GBK", "汉字内码扩展规范");

    /**
     * 字符集编码名称
     */
    private final String value;

    /**
     * 字符集描述信息
     */
    private final String info;
}