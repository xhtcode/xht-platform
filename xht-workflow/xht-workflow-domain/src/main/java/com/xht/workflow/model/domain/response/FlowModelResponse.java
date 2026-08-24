package com.xht.workflow.model.domain.response;

import com.xht.workflow.common.domain.response.WorkFlowResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 描述： 流程模型响应
 *
 * @author xht
 **/
@Data
@Schema(description = "流程模型响应")
public class FlowModelResponse extends WorkFlowResponse {

    /**
     * 模型id
     */
    @Schema(description = "模型id")
    private String modelId;

    /**
     * 模型名称
     */
    @Schema(description = "模型名称")
    private String modelName;

    /**
     * 模型key
     */
    @Schema(description = "模型key")
    private String modelKey;

    /**
     * 模型类别
     */
    @Schema(description = "模型类别")
    private String category;

    /**
     * 模型类别id
     */
    @Schema(description = "模型类别id")
    private String categoryId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 最后更新时间
     */
    @Schema(description = "最后更新时间")
    private LocalDateTime lastUpdateTime;

    /**
     * 模型版本
     */
    @Schema(description = "模型版本")
    private Integer version;

    /**
     * 模型元信息
     */
    @Schema(description = "模型元信息")
    private Map<String, Object> metaInfo;

    /**
     * 部署id
     */
    @Schema(description = "部署id")
    private String deploymentId;

    /**
     * 租户id
     */
    @Schema(description = "租户id")
    private String tenantId;

}
