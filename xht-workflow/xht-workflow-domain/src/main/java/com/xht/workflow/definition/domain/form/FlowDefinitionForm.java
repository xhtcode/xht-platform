package com.xht.workflow.definition.domain.form;

import com.xht.framework.validation.Groups;
import com.xht.workflow.common.domain.form.WorkFlowForm;
import com.xht.workflow.definition.enums.FlowDefinitionStatusEnum;
import com.xht.workflow.definition.enums.FlowDefinitionTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.io.Serial;

/**
 * 流程扩展-流程事项定义
 *
 * @author xht
 */
@Data
@Schema(description = "流程扩展-流程事项定义表单")
public class FlowDefinitionForm extends WorkFlowForm {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 事项父级ID
     */
    @NotNull(message = "事项父级ID参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "事项父级ID")
    private Long parentId;

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
     * 事项描述
     */
    @NotEmpty(message = "事项描述参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "事项描述")
    private String itemDesc;

    /**
     * 事项状态
     */
    @NotNull(message = "事项状态参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "事项状态")
    private FlowDefinitionStatusEnum itemStatus;

    /**
     * 事项排序
     */
    @NotNull(message = "事项排序参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @PositiveOrZero(message = "事项排序必须为非负数", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "事项排序")
    private Integer itemSort;

}