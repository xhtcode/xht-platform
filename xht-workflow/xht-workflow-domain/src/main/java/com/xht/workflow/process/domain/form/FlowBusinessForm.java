package com.xht.workflow.process.domain.form;

import com.xht.framework.validation.Groups;
import com.xht.workflow.common.domain.form.WorkFlowForm;
import com.xht.workflow.definition.enums.FlowDefinitionTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;

/**
 * 流程扩展-流程业务
 *
 * @author xht
 */
@Data
@Schema(description = "流程扩展-流程业务表单")
public class FlowBusinessForm extends WorkFlowForm {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 事项编码
     */
    @NotEmpty(message = "事项编码参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "事项编码")
    private String itemCode;

    /**
     * 事项名称
     */
    @NotEmpty(message = "事项名称参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "事项名称")
    private String itemName;

    /**
     * 事项类型：cate分类  order申请单据
     */
    @NotNull(message = "事项类型参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "事项类型")
    private FlowDefinitionTypeEnum itemType;

    /**
     * 流程业务号
     */
    @NotEmpty(message = "流程业务号参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "流程业务号")
    private String businessNumber;

}
