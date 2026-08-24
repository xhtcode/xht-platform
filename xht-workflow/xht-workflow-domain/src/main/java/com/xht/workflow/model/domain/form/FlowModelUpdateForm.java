package com.xht.workflow.model.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 描述： 流程模型表单
 *
 * @author xht
 **/
@Data
@Schema(description = "流程模型表单")
public class FlowModelUpdateForm extends FlowModelBaseForm {

    /**
     * 模型ID
     */
    @Schema(description = "模型ID")
    @NotEmpty(message = "模型ID不能为空")
    private String modelId;

}
