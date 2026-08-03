package com.xht.workflow.definition.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.common.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述： 监听器状态枚举
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum ListenerStatusEnum implements XhtEnum<Integer> {

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
     * 监听器状态
     */
    @JsonValue
    private final Integer value;

}

