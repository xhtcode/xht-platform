package com.xht.workflow.model.domain.form;

import com.xht.framework.common.domain.form.BasicForm;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 描述： 模型设计业务对象
 *
 * @author xht
 **/
@Data
@Schema(description = "模型设计业务对象")
public class FlowModelDesignForm extends BasicForm {

    /**
     * 模型ID
     */
    @Schema(description = "模型ID")
    @NotEmpty(message = "模型ID不能为空")
    private String modelId;

    /**
     * BPMN xml
     */
    @Schema(description = "BPMN xml")
    @NotEmpty(message = "BPMN xml不能为空")
    private String bpmnXml;

    /**
     * 是否新版本
     */
    @Schema(description = "是否新版本")
    @NotEmpty(message = "是否新版本不能为空")
    private Boolean newVersion;

}
