package com.xht.workflow.flowable.common.bo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 描述： 流程定义查询参数
 *
 * @author xht
 **/
@Getter
@Setter(AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BpmnPageQueryBO extends BpmnBO {

    /**
     * 当前页
     */
    private int current;

    /**
     * 每页显示条数
     */
    private int size;

    /**
     * 排序参数
     */
    private List<BpmnOrder> orders;

}
