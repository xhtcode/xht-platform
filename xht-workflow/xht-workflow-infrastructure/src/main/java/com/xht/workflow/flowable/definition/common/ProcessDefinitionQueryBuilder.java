package com.xht.workflow.flowable.definition.common;

import com.xht.workflow.common.domain.enums.SuspendedStatus;
import com.xht.workflow.flowable.common.bulder.BpmnPageQueryBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 描述： 部署分页查询参数
 *
 * @author xht
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProcessDefinitionQueryBuilder extends BpmnPageQueryBuilder<ProcessDefinitionPageQueryBO> {

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

    /**
     * 构建部署分页查询参数
     *
     * @return 部署分页查询参数
     */
    public static ProcessDefinitionQueryBuilder builder() {
        return new ProcessDefinitionQueryBuilder();
    }

    /**
     * 设置流程定义key
     *
     * @param processDefinitionKey 流程定义key
     * @return 构建者本身
     */
    public ProcessDefinitionQueryBuilder processDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
        return this;
    }

    /**
     * 设置流程定义名称
     *
     * @param processDefinitionName 流程定义名称
     * @return 构建者本身
     */
    public ProcessDefinitionQueryBuilder processDefinitionName(String processDefinitionName) {
        this.processDefinitionName = processDefinitionName;
        return this;
    }

    /**
     * 设置流程定义分类
     *
     * @param processDefinitionCategory 流程定义分类
     * @return 构建者本身
     */
    public ProcessDefinitionQueryBuilder processDefinitionCategory(String processDefinitionCategory) {
        this.processDefinitionCategory = processDefinitionCategory;
        return this;
    }

    /**
     * 设置流程状态
     *
     * @param suspended 流程状态
     * @return 构建者本身
     */
    public ProcessDefinitionQueryBuilder suspended(SuspendedStatus suspended) {
        this.suspended = suspended;
        return this;
    }


    /**
     * 填充查询参数
     */
    @Override
    protected ProcessDefinitionPageQueryBO createQueryData() {
        ProcessDefinitionPageQueryBO queryBO = new ProcessDefinitionPageQueryBO();
        queryBO.setProcessDefinitionKey(processDefinitionKey);
        queryBO.setProcessDefinitionName(processDefinitionName);
        queryBO.setProcessDefinitionCategory(processDefinitionCategory);
        queryBO.setSuspended(suspended);
        return queryBO;
    }
}
