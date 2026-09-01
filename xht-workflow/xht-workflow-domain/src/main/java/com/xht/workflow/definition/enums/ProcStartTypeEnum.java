package com.xht.workflow.definition.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.common.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述： 流程启动方式枚举
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum ProcStartTypeEnum implements XhtEnum<String> {

    /**
     * 按流程定义id启动
     */
    ID("id", "按流程定义id启动"),

    /**
     * 按流程定义key启动
     */
    KEY("key", "按流程定义key启动");

    @JsonValue
    private final String value;

    private final String desc;

}
