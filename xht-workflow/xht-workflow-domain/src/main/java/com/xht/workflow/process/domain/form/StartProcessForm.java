package com.xht.workflow.process.domain.form;

import com.xht.workflow.common.domain.form.WorkFlowForm;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述： 工作流表单
 *
 * @author xht
 **/
@Data
@Schema(description = "工作流表单")
public class StartProcessForm extends WorkFlowForm {

    @Schema(description = "事项定义ID")
    private String itemDefId;

}
