package com.xht.workflow.flowable.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.common.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述： 挂起状态
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum SuspendedStatus implements XhtEnum<Integer> {

    /**
     * 正常
     */
    ACTIVE(1, "正常"),

    /**
     * 挂起
     */
    SUSPENDED(2, "挂起");

    @JsonValue
    private final Integer value;

    private final String desc;

}
