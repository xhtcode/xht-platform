package com.xht.platform.dict.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.common.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 子节点是否显示禁用状态
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum DictShowDisabledEnum implements XhtEnum<Integer> {

    /**
     * 不显示
     */
    DISABLED(0),

    /**
     * 显示
     */
    SHOW(1),
    ;
    /**
     * 字典状态
     */
    @JsonValue
    private final Integer value;

}
