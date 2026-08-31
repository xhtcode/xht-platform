package com.xht.workflow.definition.domain.form;

import com.xht.framework.validation.Groups;
import com.xht.workflow.common.domain.form.WorkFlowForm;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;

/**
 * 流程扩展-流程表单
 *
 * @author xht
 */
@Data
@Schema(description = "流程扩展-流程表单")
public class FlowFormForm extends WorkFlowForm {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 表单名称
     */
    @NotEmpty(message = "表单名称参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "表单名称")
    private String formName;

    /**
     * 表单内容
     */
    @NotNull(message = "表单内容参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "表单内容")
    private String formContent;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

}
