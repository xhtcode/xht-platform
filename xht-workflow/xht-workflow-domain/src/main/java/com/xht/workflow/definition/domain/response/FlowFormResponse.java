package com.xht.workflow.definition.domain.response;

import com.xht.framework.common.domain.response.MetaResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * 流程扩展-流程表单
 *
 * @author xht
 */
@Data
@Schema(description = "流程扩展-流程表单响应")
public class FlowFormResponse extends MetaResponse {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 表单ID
     */
    @Schema(description = "表单ID")
    private Long id;

    /**
     * 表单名称
     */
    @Schema(description = "表单名称")
    private String formName;

    /**
     * 表单内容
     */
    @Schema(description = "表单内容")
    private String formContent;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

}
