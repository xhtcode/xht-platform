package com.xht.workflow.definition.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.common.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述：cate分类 order申请单据
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum FlowDefinitionTypeEnum implements XhtEnum<String> {

    /**
     * 申请单据
     */
    ORDER("order", "申请单据"),

    /**
     * 分类
     */
    CATEGORY("category", "分类");;

    @JsonValue
    private final String value;

    private final String desc;

}
