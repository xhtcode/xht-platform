package com.xht.workflow.definition.domain.response;

import com.xht.framework.common.domain.response.MetaResponse;
import com.xht.workflow.definition.enums.DefinitionStatusEnum;
import com.xht.workflow.definition.enums.DefinitionTypeEnum;
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
public class FlowDefinitionResponse extends MetaResponse {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 流程定义id
     */
    @Schema(description = "流程定义id")
    private Long id;

    /**
     * 流程定义父级ID
     */
    @Schema(description = "流程定义父级ID")
    private Long parentId;

    /**
     * 流程定义编码
     */
    @Schema(description = "流程定义编码")
    private String definitionCode;

    /**
     * 流程定义名称
     */
    @Schema(description = "流程定义名称")
    private String definitionName;

    /**
     * 流程定义类型：cate分类  order申请单据
     */
    @Schema(description = "流程定义类型")
    private DefinitionTypeEnum definitionType;

    /**
     * 流程定义描述
     */
    @Schema(description = "流程定义描述")
    private String definitionDesc;

    /**
     * 流程定义状态
     */
    @Schema(description = "流程定义状态")
    private DefinitionStatusEnum definitionStatus;

    /**
     * 流程定义排序
     */
    @Schema(description = "流程定义排序")
    private Integer definitionSort;

}