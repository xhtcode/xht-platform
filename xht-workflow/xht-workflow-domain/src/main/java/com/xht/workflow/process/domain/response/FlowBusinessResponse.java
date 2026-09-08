package com.xht.workflow.process.domain.response;

import com.xht.framework.common.domain.response.MetaResponse;
import com.xht.workflow.definition.enums.FlowDefinitionTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * 流程扩展-流程业务
 *
 * @author xht
 */
@Data
@Schema(description = "流程扩展-流程业务响应")
public class FlowBusinessResponse extends MetaResponse {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 业务ID
     */
    @Schema(description = "业务ID")
    private Long id;

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
     * 流程业务号
     */
    @Schema(description = "流程业务号")
    private String businessNumber;

}
