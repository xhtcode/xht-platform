package com.xht.workflow.definition.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.common.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述： 监听器脚本类型枚举 内联外联
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum ListenerScriptTypeEnum implements XhtEnum<String> {

    INLINE("inline", "内联脚本"),

    EXTERNAL("external", "外联脚本");

    /**
     * 脚本类型
     */
    @JsonValue
    private final String value;

    private final String desc;
}
