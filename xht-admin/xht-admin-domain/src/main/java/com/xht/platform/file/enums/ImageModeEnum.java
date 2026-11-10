package com.xht.platform.file.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.common.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述： 图片处理模式
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum ImageModeEnum implements XhtEnum<String> {

    /**
     * 原图直出，浏览器不兼容格式转 WebP
     */
    ORIGINAL("original", "原图"),

    /**
     * 统一转 WebP
     */
    WEBP("webp", "WebP"),

    /**
     * 单图转 PDF
     */
    PDF("pdf", "PDF"),
    ;

    @JsonValue
    private final String value;

    private final String desc;

    @JsonCreator
    public static ImageModeEnum getByValue(String value) {
        if (value == null) {
            return null;
        }
        for (ImageModeEnum e : values()) {
            if (e.value.equalsIgnoreCase(value)) {
                return e;
            }
        }
        return null;
    }

}
