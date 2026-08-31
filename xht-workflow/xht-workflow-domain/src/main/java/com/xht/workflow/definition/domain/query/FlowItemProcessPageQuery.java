package com.xht.workflow.definition.domain.query;

import com.xht.framework.common.domain.query.PageBasicQuery;
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
@Schema(description = "流程扩展-流程定义查询")
public class FlowItemProcessPageQuery extends PageBasicQuery {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 事项定义主键id
     */
    @Schema(description = "事项定义主键id")
    private Long itemDefId;

    /**
     * 路由名称
     */
    @Schema(description = "路由名称")
    private String routerName;

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
     * 启用状态
     */
    @Schema(description = "启用状态")
    private EnableStatusEnum enableStatus;

}
