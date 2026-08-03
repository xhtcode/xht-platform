package com.xht.workflow.sequence.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.common.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述： 是否循环枚举
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum IsCycleEnums implements XhtEnum<Integer> {

    YES(0, "是"),

    NO(1, "否");

    @JsonValue
    private final Integer value;

    private final String desc;

}
