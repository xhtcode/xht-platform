package com.xht.workflow.flowable.process.common;

import com.xht.workflow.flowable.common.dto.BpmnDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 描述： 流程实例DTO
 *
 * @author xht
 **/
@Data
public class ProcessInstanceDTO extends BpmnDTO {

    /**
     * 流程实例ID
     */
    private String processInstanceId;

    /**
     * 流程实例名称
     */
    private String name;

    /**
     * 流程定义ID
     */
    private String processDefId;

    /**
     * 流程定义key
     */
    private String processDefKey;

    /**
     * 流程定义名称
     */
    private String processDefName;

    /**
     * 流程定义版本
     */
    private Integer processDefVersion;

    /**
     * 部署ID
     */
    private String deploymentId;

    /**
     * 业务key
     */
    private String businessKey;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 是否已结束
     */
    private Boolean ended;

    /**
     * 发起人用户ID
     */
    private String startUserId;

    /**
     * 流程变量
     */
    private Map<String, Object> processVariables;
}
