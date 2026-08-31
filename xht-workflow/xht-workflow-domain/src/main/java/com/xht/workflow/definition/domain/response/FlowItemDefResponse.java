package com.xht.workflow.definition.domain.response;

import com.xht.framework.common.domain.response.MetaResponse;
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
@Schema(description = "流程扩展-流程事项定义响应")
public class FlowItemDefResponse extends MetaResponse {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 事项主键id
     */
    @Schema(description = "事项主键id")
    private Long id;

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
     * 事项描述
     */
    @Schema(description = "事项描述")
    private String itemDesc;

    /**
     * 事项状态
     */
    @Schema(description = "事项状态")
    private FlowDefinitionStatusEnum itemStatus;

    /**
     * 事项排序号，数值越大越靠前
     */
    @Schema(description = "事项排序")
    private Integer itemSort;

}