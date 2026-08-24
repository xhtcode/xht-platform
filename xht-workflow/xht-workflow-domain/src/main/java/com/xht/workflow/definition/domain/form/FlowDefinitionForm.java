package com.xht.workflow.definition.domain.form;

import com.xht.framework.common.domain.form.BasicForm;
import com.xht.framework.validation.Groups;
import com.xht.workflow.definition.enums.DefinitionStatusEnum;
import com.xht.workflow.definition.enums.DefinitionTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serial;

/**
 * 流程扩展-流程定义
 *
 * @author xht
 */
@Data
@Schema(description = "流程扩展-流程定义表单")
public class FlowDefinitionForm extends BasicForm {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 流程定义父级ID
     */
    @NotNull(message = "流程定义父级ID参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "流程定义父级ID")
    private Long parentId;

    /**
     * 流程定义编码
     */
    @NotEmpty(message = "流程定义编码参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "流程定义编码")
    private String definitionCode;

    /**
     * 流程定义名称
     */
    @NotEmpty(message = "流程定义名称参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "流程定义名称")
    private String definitionName;

    /**
     * 流程定义类型：cate分类  order申请单据
     */
    @NotNull(message = "流程定义类型参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "流程定义类型")
    private DefinitionTypeEnum definitionType;

    /**
     * 流程定义描述
     */
    @NotEmpty(message = "流程定义描述参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "流程定义描述")
    private String definitionDesc;

    /**
     * 流程定义状态
     */
    @NotNull(message = "流程定义状态参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "流程定义状态")
    private DefinitionStatusEnum definitionStatus;

    /**
     * 流程定义排序
     */
    @NotNull(message = "流程定义排序参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @PositiveOrZero(message = "流程定义排序必须为非负数", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "流程定义排序")
    private Integer definitionSort;

}