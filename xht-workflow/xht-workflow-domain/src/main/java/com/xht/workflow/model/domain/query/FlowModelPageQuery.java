package com.xht.workflow.model.domain.query;

import com.xht.framework.common.domain.query.PageBasicQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述： 流程模型分页查询
 *
 * @author xht
 **/
@Data
@Schema(description = "流程模型分页查询")
public class FlowModelPageQuery extends PageBasicQuery {

    /**
     * 模型名称
     */
    @Schema(description = "模型名称")
    private String modelName;

    /**
     * 模型Key（流程定义标识）
     */
    @Schema(description = "模型Key（流程定义标识）")
    private String modelKey;

    /**
     * 模型分类
     */
    @Schema(description = "模型分类")
    private String category;

}
