package com.xht.workflow.process.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.common.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述：流程时限类型
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum FlowTimeLimitTypeEnum implements XhtEnum<String> {

    /**
     * 自然日
     */
    NATURAL("natural", "自然日"),

    /**
     * 工作日(跳过周末顺延)
     */
    WORKDAY("workday", "工作日");

    @JsonValue
    private final String value;

    private final String desc;

}
