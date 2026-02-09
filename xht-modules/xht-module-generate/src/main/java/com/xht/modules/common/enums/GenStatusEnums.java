package com.xht.modules.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.core.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum GenStatusEnums implements XhtEnum<Integer> {

    NO(0, "否"),

    YES(1, "是"),

    OTHER(99, "其他");

    @JsonValue
    private final Integer value;

    private final String desc;

    public static GenStatusEnums of(Integer value) {
        for (GenStatusEnums item : values()) {
            if (item.value.equals(value)) {
                return item;
            }
        }
        return null;
    }
}
