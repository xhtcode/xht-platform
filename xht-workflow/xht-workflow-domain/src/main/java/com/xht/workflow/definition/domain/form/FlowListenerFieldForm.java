package com.xht.workflow.definition.domain.form;

import com.xht.framework.common.domain.form.BasicForm;
import com.xht.framework.validation.Groups;
import com.xht.framework.validation.enums.XhtEnumValidator;
import com.xht.workflow.definition.enums.ListenerFieldStatusEnum;
import com.xht.workflow.definition.enums.ListenerFieldTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * 描述： 流程扩展-监听器（字段管理）表单请求参数
 *
 * @author xht
 **/
@Data
@Schema(description = "流程扩展-监听器（字段管理）表单请求参数")
public class FlowListenerFieldForm extends BasicForm {

    /**
     * 监听器字段id
     */
    @Schema(description = "监听器字段id")
    @Null(message = "监听器字段id必须为空", groups = {Groups.Create.class})
    @NotNull(message = "监听器字段id参数不合法", groups = {Groups.Update.class})
    @Positive(message = "监听器字段id参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    private Long id;

    /**
     * 监听器id
     */
    @Schema(description = "监听器id")
    @NotNull(message = "监听器id不能为空", groups = {Groups.Create.class, Groups.Update.class})
    private Long listenerId;

    /**
     * 字段名称
     */
    @Schema(description = "字段名称")
    @NotEmpty(message = "字段名称不能为空", groups = {Groups.Create.class, Groups.Update.class})
    private String fieldName;

    /**
     * 字段类型(字符串、表达式)
     */
    @Schema(description = "字段类型(字符串、表达式)")
    @XhtEnumValidator(value = ListenerFieldTypeEnum.class, message = "字段类型不能为空", groups = {Groups.Create.class, Groups.Update.class})
    private ListenerFieldTypeEnum fieldType;

    /**
     * 字段值
     */
    @Schema(description = "字段值")
    @NotEmpty(message = "字段值不能为空", groups = {Groups.Create.class, Groups.Update.class})
    private String fieldValue;

    /**
     * 字段排序
     */
    @Schema(description = "字段排序")
    @NotNull(message = "字段排序不能为空", groups = {Groups.Create.class, Groups.Update.class})
    @Positive(message = "监听器字段id参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    private Integer fieldOrder;

    /**
     * 字段状态
     */
    @Schema(description = "字段状态")
    @XhtEnumValidator(value = ListenerFieldStatusEnum.class, message = "字段状态不能为空", groups = {Groups.Create.class, Groups.Update.class})
    private ListenerFieldStatusEnum fieldStatus;

}
