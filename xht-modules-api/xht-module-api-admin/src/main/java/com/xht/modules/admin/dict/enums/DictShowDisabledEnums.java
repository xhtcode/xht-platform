package com.xht.modules.admin.dict.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.core.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 子节点是否显示禁用状态
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum DictShowDisabledEnums implements XhtEnum<Integer> {

    /**
     * 禁用
     */
    DISABLED(0),

    /**
     * 启用
     */
    SHOW(1),
    ;
    /**
     * 字典状态
     */
    @JsonValue
    private final Integer value;
}
