package com.xht.workflow.model.domain.form;

import com.xht.framework.common.domain.form.BasicForm;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Map;

/**
 * 描述： 流程模型表单
 *
 * @author xht
 **/
@Data
@Schema(description = "流程模型表单")
public class FlowModelUpdateForm extends BasicForm {

    /**
     * 模型ID
     */
    @Schema(description = "模型ID")
    @NotEmpty(message = "模型ID不能为空")
    private String modelId;

    /**
     * 模型分类
     */
    @Schema(description = "模型分类")
    @NotEmpty(message = "模型分类不能为空")
    private String categoryId;

    /**
     * 模型元信息
     */
    @Schema(description = "模型元信息")
    @NotEmpty(message = "模型元信息不能为空")
    private Map<String, Object> metaInfo;

}
