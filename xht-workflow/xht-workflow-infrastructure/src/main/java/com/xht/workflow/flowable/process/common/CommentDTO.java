package com.xht.workflow.flowable.process.common;

import com.xht.workflow.flowable.common.dto.BpmnDTO;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 描述： 流程意见DTO
 *
 * @author xht
 **/
@Data
public class CommentDTO extends BpmnDTO {

    /**
     * 意见ID
     */
    private String id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 意见时间
     */
    private LocalDateTime time;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 流程实例ID
     */
    private String processInstanceId;

    /**
     * 意见类型
     */
    private String type;

    /**
     * 完整意见内容
     */
    private String fullMessage;
}
