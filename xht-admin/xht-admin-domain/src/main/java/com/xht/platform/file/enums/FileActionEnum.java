package com.xht.platform.file.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.common.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述： 文件操作动作
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum FileActionEnum implements XhtEnum<String> {

    /**
     * 预览
     */
    PREVIEW("PREVIEW", "预览"),

    /**
     * 下载
     */
    DOWNLOAD("DOWNLOAD", "下载"),

    /**
     * 打印
     */
    PRINT("PRINT", "打印"),

    /**
     * 分享
     */
    SHARE("SHARE", "分享"),
    ;

    @JsonValue
    private final String value;

    private final String desc;

    @JsonCreator
    public static FileActionEnum getByValue(String value) {
        if (value == null) {
            return null;
        }
        for (FileActionEnum e : values()) {
            if (e.value.equalsIgnoreCase(value)) {
                return e;
            }
        }
        return null;
    }

}
