package com.xht.workflow.flowable.definition.common;

import com.xht.workflow.flowable.common.dto.BpmnDTO;
import com.xht.workflow.common.domain.enums.SuspendedStatus;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 描述： 流程定义DTO
 *
 * @author xht
 **/
@Data
public class ProcessDefinitionDTO extends BpmnDTO {

    /**
     * 流程定义ID
     */
    private String processDefId;

    /**
     * 流程分类，取自bpmn flowable:category属性，用于业务分组
     */
    private String processDefCategory;

    /**
     * 流程定义名称
     */
    private String processDefName;

    /**
     * 流程定义key
     */
    private String processDefKey;

    /**
     * 流程定义描述
     */
    private String processDefDescription;

    /**
     * 流程定义版本
     */
    private int processDefVersion;

    /**
     * 是否挂起
     */
    private SuspendedStatus suspendedStatus;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 部署ID
     */
    private String deploymentId;

    /**
     * 部署名称
     */
    private String deploymentName;

    /**
     * 部署时间
     */
    private LocalDateTime deploymentTime;

    /**
     * 部署分类
     */
    private String deploymentCategory;

    /**
     * 部署key
     */
    private String deploymentKey;
}
