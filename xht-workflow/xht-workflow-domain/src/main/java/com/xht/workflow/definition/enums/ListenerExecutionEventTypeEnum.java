package com.xht.workflow.definition.enums;

import com.xht.framework.common.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述： 执行监听器 事件类型
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum ListenerExecutionEventTypeEnum implements XhtEnum<String> {

    START("start"),

    END("end")
    ;

    private final String value;
}
