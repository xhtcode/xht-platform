package com.xht.platform.file.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.common.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述： 文件访问主体类型
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum FileAccessSubjectTypeEnum implements XhtEnum<String> {

    /**
     * 登录用户
     */
    USER("USER", "登录用户"),

    /**
     * 匿名公开访问
     */
    ANON("ANON", "匿名公开访问"),
    ;

    @JsonValue
    private final String value;

    private final String desc;

    @JsonCreator
    public static FileAccessSubjectTypeEnum getByValue(String value) {
        if (value == null) {
            return null;
        }
        for (FileAccessSubjectTypeEnum e : values()) {
            if (e.value.equalsIgnoreCase(value)) {
                return e;
            }
        }
        return null;
    }

}
