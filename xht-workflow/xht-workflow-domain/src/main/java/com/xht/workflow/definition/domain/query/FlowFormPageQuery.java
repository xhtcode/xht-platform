package com.xht.workflow.definition.domain.query;

import com.xht.framework.common.domain.query.PageBasicQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * 流程扩展-流程表单
 *
 * @author xht
 */
@Data
@Schema(description = "流程扩展-流程表单查询")
public class FlowFormPageQuery extends PageBasicQuery {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 表单名称
     */
    @Schema(description = "表单名称")
    private String formName;

}
