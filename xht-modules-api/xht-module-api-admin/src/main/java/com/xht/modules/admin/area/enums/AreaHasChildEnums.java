package com.xht.modules.admin.area.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.core.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 区划下级是否存在子区划枚举
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum AreaHasChildEnums implements XhtEnum<Integer> {

    NO_CHILD(0, "无子区划"),
    HAS_CHILD(1, "有子区划");

    @JsonValue
    private final Integer value;
    private final String desc;
}
