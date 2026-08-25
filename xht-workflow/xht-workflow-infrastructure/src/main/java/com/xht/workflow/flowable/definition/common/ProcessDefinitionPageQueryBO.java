package com.xht.workflow.flowable.definition.common;

import com.xht.workflow.flowable.common.bo.BpmnPageQueryBO;
import com.xht.workflow.common.domain.enums.SuspendedStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 描述： 部署业务对象
 *
 * @author xht
 **/
@Getter
@Setter(AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProcessDefinitionPageQueryBO extends BpmnPageQueryBO {

    /**
     * 流程定义key
     */
    private String processDefinitionKey;

    /**
     * 流程定义名称
     */
    private String processDefinitionName;

    /**
     * 流程分类
     */
    private String processDefinitionCategory;

    /**
     * 流程状态
     */
    private SuspendedStatus suspended;

}
