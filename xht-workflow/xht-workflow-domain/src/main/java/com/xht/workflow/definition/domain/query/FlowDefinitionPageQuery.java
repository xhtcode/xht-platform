package com.xht.workflow.definition.domain.query;

import com.xht.framework.common.domain.query.BasicQuery;
import com.xht.workflow.definition.enums.FlowDefinitionStatusEnum;
import com.xht.workflow.definition.enums.FlowDefinitionTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * 流程扩展-流程事项定义
 *
 * @author xht
 */
@Data
@Schema(description = "流程扩展-流程事项定义查询")
public class FlowDefinitionPageQuery extends BasicQuery {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 事项父级ID
     */
    @Schema(description = "事项父级ID")
    private Long parentId;

    /**
     * 事项编码
     */
    @Schema(description = "事项编码")
    private String itemCode;

    /**
     * 事项名称
     */
    @Schema(description = "事项名称")
    private String itemName;

    /**
     * 事项类型：cate分类  order申请单据
     */
    @Schema(description = "事项类型")
    private FlowDefinitionTypeEnum itemType;

    /**
     * 事项状态
     */
    @Schema(description = "事项状态")
    private FlowDefinitionStatusEnum itemStatus;

}