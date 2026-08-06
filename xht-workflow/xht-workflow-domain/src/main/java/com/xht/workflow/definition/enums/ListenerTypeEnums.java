package com.xht.workflow.definition.enums;

import com.xht.framework.common.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述： 监听器类型枚举
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum ListenerTypeEnums implements XhtEnum<String> {

    /**
     * java 类
     */
    JAVA_CLASS("java", "java 类"),
    /**
     * 表达式
     */
    EXPRESSION("expression", "表达式"),
    /**
     * 代理表达式
     */
    DELEGATE_EXPRESSION("delegate_expression", "代理表达式"),
    /**
     * 脚本
     */
    SCRIPT("script", "脚本"),
    ;

    private final String value;

    private final String desc;

}
