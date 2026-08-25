package com.xht.workflow.definition.domain.response;

import com.xht.workflow.common.domain.enums.SuspendedStatus;
import com.xht.workflow.common.domain.response.WorkFlowResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 描述： 流程定义响应
 *
 * @author xht
 **/
@Data
@Schema(description = "流程定义响应")
public class ProcessDefinitionResponse extends WorkFlowResponse {

    /**
     * 流程定义ID
     */
    @Schema(description = "流程定义ID")
    private String processDefId;

    /**
     * 流程分类，取自bpmn flowable:category属性，用于业务分组
     */
    @Schema(description = "流程分类，取自bpmn flowable:category属性，用于业务分组")
    private String processDefCategory;

    /**
     * 流程定义名称
     */
    @Schema(description = "流程定义名称")
    private String processDefName;

    /**
     * 流程定义key
     */
    @Schema(description = "流程定义key")
    private String processDefKey;

    /**
     * 流程定义描述
     */
    @Schema(description = "流程定义描述")
    private String processDefDescription;

    /**
     * 流程定义版本
     */
    @Schema(description = "流程定义版本")
    private int processDefVersion;

    /**
     * 是否挂起
     */
    @Schema(description = "是否挂起")
    private SuspendedStatus suspendedStatus;

    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    private String tenantId;

    /**
     * 部署ID
     */
    @Schema(description = "部署ID")
    private String deploymentId;

    /**
     * 部署名称
     */
    @Schema(description = "部署名称")
    private String deploymentName;

    /**
     * 部署时间
     */
    @Schema(description = "部署时间")
    private LocalDateTime deploymentTime;

    /**
     * 部署分类
     */
    @Schema(description = "部署分类")
    private String deploymentCategory;

    /**
     * 部署key
     */
    @Schema(description = "部署key")
    private String deploymentKey;
}
