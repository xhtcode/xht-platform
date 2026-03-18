package com.xht.modules.admin.dict.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.core.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 字典状态枚举
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum DictStatusEnums implements XhtEnum<Integer> {

    /**
     * 启用
     */
    ENABLE(1),

    /**
     * 禁用
     */
    DISABLE(2),
    ;

    /**
     * 字典状态
     */
    @JsonValue
    private final Integer value;

}
