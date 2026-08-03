package com.xht.workflow.definition.domain.query;

import com.xht.framework.common.domain.query.BasicQuery;
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
@Schema(description = "流程扩展-流程定义查询")
public class FlowDefinitionPageQuery extends BasicQuery {

    @Serial
    private static final long serialVersionUID = 1L;

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
     * 流程定义状态
     */
    @Schema(description = "流程定义状态")
    private DefinitionStatusEnum definitionStatus;

}