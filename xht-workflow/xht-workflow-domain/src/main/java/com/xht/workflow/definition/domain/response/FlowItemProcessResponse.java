package com.xht.workflow.definition.domain.response;

import com.xht.framework.common.domain.response.MetaResponse;
import com.xht.framework.common.enums.DefaultStatusEnum;
import com.xht.framework.common.enums.EnableStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * 流程扩展-流程定义
 *
 * @author xht
 */
@Data
@Schema(description = "流程扩展-流程定义响应")
public class FlowItemProcessResponse extends MetaResponse {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @Schema(description = "主键id")
    private Long id;

    /**
     * 事项定义主键id
     */
    @Schema(description = "事项定义主键id")
    private Long itemDefId;

    /**
     * 路由主键
     */
    @Schema(description = "路由主键")
    private String routerKey;

    /**
     * 路由名称
     */
    @Schema(description = "路由名称")
    private String routerName;

    /**
     * 流程启动方式id或者key
     */
    @Schema(description = "流程启动方式id或者key")
    private String procStartType;

    /**
     * 流程定义id
     */
    @Schema(description = "流程定义id")
    private String procDefId;

    /**
     * 流程定义key
     */
    @Schema(description = "流程定义key")
    private String procDefKey;

    /**
     * 流程定义名称
     */
    @Schema(description = "流程定义名称")
    private String procDefName;

    /**
     * 流程定义版本
     */
    @Schema(description = "流程定义版本")
    private Integer procDefVersion;

    /**
     * 匹配优先级
     */
    @Schema(description = "匹配优先级")
    private Integer procDefPriority;

    /**
     * 流程部署id
     */
    @Schema(description = "流程部署id")
    private String deploymentId;

    /**
     * 默认状态
     */
    @Schema(description = "默认状态")
    private DefaultStatusEnum defaultStatus;

    /**
     * 启用状态
     */
    @Schema(description = "启用状态")
    private EnableStatusEnum enableStatus;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

}
