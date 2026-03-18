package com.xht.modules.admin.system.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.core.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜单通用状态枚举类
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum MenuCommonStatus implements XhtEnum<Integer> {

    /**
     * 否
     */
    NO(0, false),

    /**
     * 是
     */
    YES(1, true),

    ;

    @JsonValue
    private final Integer value;

    private final Boolean status;
}
