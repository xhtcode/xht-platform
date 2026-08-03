package com.xht.workflow.definition.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.common.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述： 类别状态枚举
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum DefinitionStatusEnum implements XhtEnum<Integer> {
    /**
     * 正常
     */
    NORMAL(1, "正常"),

    /**
     * 禁用
     */
    DISABLE(0, "禁用");

    /**
     * 状态
     */
    @JsonValue
    private final Integer value;

    /**
     * 描述
     */
    private final String desc;
}
