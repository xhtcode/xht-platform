package com.xht.workflow.flowable.process.common;

import com.xht.workflow.flowable.common.dto.BpmnDTO;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 描述： 历史任务DTO
 *
 * @author xht
 **/
@Data
public class HistoricTaskDTO extends BpmnDTO {

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 任务定义key
     */
    private String taskDefinitionKey;

    /**
     * 流程实例ID
     */
    private String processInstanceId;

    /**
     * 流程定义ID
     */
    private String processDefinitionId;

    /**
     * 执行实例ID
     */
    private String executionId;

    /**
     * 办理人
     */
    private String assignee;

    /**
     * 任务发起人
     */
    private String owner;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 处理耗时（毫秒）
     */
    private Long durationInMillis;

    /**
     * 删除原因
     */
    private String deleteReason;

    /**
     * 认领时间
     */
    private LocalDateTime claimTime;

    /**
     * 到期时间
     */
    private LocalDateTime dueDate;

    /**
     * 优先级
     */
    private Integer priority;
}
