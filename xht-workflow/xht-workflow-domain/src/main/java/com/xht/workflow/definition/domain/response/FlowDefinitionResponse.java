package com.xht.workflow.definition.domain.response;

import com.xht.framework.common.domain.response.MetaResponse;
import com.xht.workflow.definition.enums.DefinitionStatusEnum;
import com.xht.workflow.definition.enums.DefinitionTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * 流程分类
 *
 * @author xht
 */
@Data
@Schema(description = "流程分类响应")
public class FlowDefinitionResponse extends MetaResponse {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 类别id
     */
    @Schema(description = "类别id")
    private Long id;

    /**
     * 类别父级ID
     */
    @Schema(description = "类别父级ID")
    private Long parentId;

    /**
     * 类别编码
     */
    @Schema(description = "类别编码")
    private String definitionCode;

    /**
     * 类别名称
     */
    @Schema(description = "类别名称")
    private String definitionName;

    /**
     * 类别类型：cate分类  order申请单据
     */
    @Schema(description = "类别类型")
    private DefinitionTypeEnum definitionType;

    /**
     * 类别描述
     */
    @Schema(description = "类别描述")
    private String definitionDesc;

    /**
     * 类别状态
     */
    @Schema(description = "类别状态")
    private DefinitionStatusEnum definitionStatus;

    /**
     * 类别排序
     */
    @Schema(description = "类别排序")
    private Integer definitionSort;

}