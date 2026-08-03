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
 * 流程分类
 *
 * @author xht
 */
@Data
@Schema(description = "流程分类表单")
public class FlowDefinitionForm extends BasicForm {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 类别id
     */
    @Null(message = "类别`id`唯一标识必须为空", groups = {Groups.Create.class})
    @NotNull(message = "类别`id`唯一标识参数不合法", groups = {Groups.Update.class})
    @Positive(message = "类别`id`唯一标识参数不合法", groups = {Groups.Update.class})
    @Schema(description = "类别id")
    private Long id;

    /**
     * 类别父级ID
     */
    @NotNull(message = "类别父级ID参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Positive(message = "类别父级ID参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "类别父级ID")
    private Long parentId;

    /**
     * 类别编码
     */
    @NotEmpty(message = "类别编码参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "类别编码")
    private String definitionCode;

    /**
     * 类别名称
     */
    @NotEmpty(message = "类别名称参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "类别名称")
    private String definitionName;

    /**
     * 类别类型：cate分类  order申请单据
     */
    @NotNull(message = "类别类型参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "类别类型")
    private DefinitionTypeEnum definitionType;

    /**
     * 类别描述
     */
    @NotEmpty(message = "类别描述参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "类别描述")
    private String definitionDesc;

    /**
     * 类别状态
     */
    @NotNull(message = "类别状态参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "类别状态")
    private DefinitionStatusEnum definitionStatus;

    /**
     * 类别排序
     */
    @NotNull(message = "类别排序参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @PositiveOrZero(message = "类别排序必须为非负数", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "类别排序")
    private Integer definitionSort;

}