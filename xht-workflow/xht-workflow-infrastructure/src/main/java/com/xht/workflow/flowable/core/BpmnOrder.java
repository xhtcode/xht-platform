package com.xht.workflow.flowable.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述： 流程定义排序参数
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public final class BpmnOrder extends BpmnBO {

    /**
     * 排序字段
     */
    private String name;

    /**
     * 排序类型
     */
    private BpmnOrderType orderType;

    /**
     * 排序类型
     * @author xht
     */
    public static enum BpmnOrderType {
        ASC,
        DESC;
    }

}