package com.xht.workflow.flowable.core.model;

import com.xht.workflow.flowable.core.BpmnDTO;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 描述： 流程模型DTO
 *
 * @author xht
 **/
@Data
public class ModelDTO extends BpmnDTO {

    /**
     * 模型id
     */
    private String modelId;

    /**
     * 模型名称
     */
    private String modelName;

    /**
     * 模型key
     */
    private String modelKey;

    /**
     * 模型类别
     */
    private String category;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最后更新时间
     */
    private LocalDateTime lastUpdateTime;

    /**
     * 模型版本
     */
    private Integer version;

    /**
     * 模型元信息
     */
    private String metaInfo;

    /**
     * 部署id
     */
    private String deploymentId;

    /**
     * 租户id
     */
    private String tenantId;
}
