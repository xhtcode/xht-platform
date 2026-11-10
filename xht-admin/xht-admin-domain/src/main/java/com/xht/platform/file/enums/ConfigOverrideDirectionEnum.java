package com.xht.platform.file.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.common.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述： 配置覆盖方向
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum ConfigOverrideDirectionEnum implements XhtEnum<String> {

    /**
     * 仅管理员可改，用户不可覆盖
     */
    LOCKED("LOCKED", "锁定"),

    /**
     * 用户只能收紧，不能放宽
     */
    TIGHTEN_ONLY("TIGHTEN_ONLY", "只能收紧"),

    /**
     * 用户在范围内自由设置
     */
    FREE("FREE", "自由设置"),
    ;

    @JsonValue
    private final String value;

    private final String desc;

    @JsonCreator
    public static ConfigOverrideDirectionEnum getByValue(String value) {
        if (value == null) {
            return null;
        }
        for (ConfigOverrideDirectionEnum e : values()) {
            if (e.value.equalsIgnoreCase(value)) {
                return e;
            }
        }
        return null;
    }

}
