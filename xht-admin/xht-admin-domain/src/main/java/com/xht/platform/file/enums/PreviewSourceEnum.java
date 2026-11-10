package com.xht.platform.file.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.common.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述： 预览来源
 * <p>original：直接使用原文件；convert：使用转换产物（PDF/WebP）</p>
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum PreviewSourceEnum implements XhtEnum<String> {

    /**
     * 原文件
     */
    ORIGINAL("original", "原文件"),

    /**
     * 转换产物
     */
    CONVERT("convert", "转换产物"),
    ;

    /**
     * 来源编码（入库值）
     */
    @JsonValue
    private final String value;

    /**
     * 来源名称
     */
    private final String desc;

    /**
     * jackson 反序列化
     *
     * @param value 来源编码
     * @return 枚举，未匹配返回 null
     */
    @JsonCreator
    public static PreviewSourceEnum getByValue(String value) {
        if (value == null) {
            return null;
        }
        for (PreviewSourceEnum e : values()) {
            if (e.value.equalsIgnoreCase(value)) {
                return e;
            }
        }
        return null;
    }

}
