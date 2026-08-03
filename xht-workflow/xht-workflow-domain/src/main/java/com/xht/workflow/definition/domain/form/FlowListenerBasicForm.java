package com.xht.workflow.definition.domain.form;

import com.xht.framework.common.domain.form.BasicForm;
import com.xht.framework.common.enums.XhtEnum;
import com.xht.framework.validation.Groups;
import com.xht.workflow.definition.enums.ListenerScriptTypeEnum;
import com.xht.workflow.definition.enums.ListenerStatusEnum;
import com.xht.workflow.definition.enums.ListenerTypeEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * 描述： 流程监听器表单
 *
 * @author xht
 **/
@Data
@Schema(description = "流程监听器表单")
public class FlowListenerBasicForm<T extends XhtEnum<String>> extends BasicForm {

    /**
     * 序列id
     */
    @Null(message = "序列id必须为空", groups = {Groups.Create.class})
    @NotNull(message = "序列id参数不合法", groups = {Groups.Update.class})
    @Positive(message = "序列id参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "序列id")
    private Long id;

    /**
     * 事件类型
     */
    @NotNull(message = "事件类型参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "事件类型")
    private T eventType;

    /**
     * 监听器类型
     */
    @NotEmpty(message = "监听器类型参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "监听器类型")
    private ListenerTypeEnums listenerType;

    /**
     * java类
     */
    @NotEmpty(message = "java类参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "java类")
    private String javaClass;

    /**
     * 表达式
     */
    @NotEmpty(message = "表达式参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "表达式")
    private String expressionValue;

    /**
     * 代理表达式
     */
    @NotEmpty(message = "代理表达式参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "代理表达式")
    private String delegateExpression;

    /**
     * 脚本格式
     */
    @NotEmpty(message = "脚本格式参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "脚本格式")
    private String scriptFormat;

    /**
     * 脚本类型
     */
    @NotNull(message = "脚本类型参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "脚本类型")
    private ListenerScriptTypeEnum scriptType;

    /**
     * 内联脚本内容
     */
    @NotEmpty(message = "内联脚本内容参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "内联脚本内容")
    private String scriptContent;

    /**
     * 外部脚本资源路径
     */
    @NotEmpty(message = "外部脚本资源路径参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "外部脚本资源路径")
    private String scriptResource;

    /**
     * 监听器顺序
     */
    @NotNull(message = "监听器顺序参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "监听器顺序")
    private Integer listenerOrder;

    /**
     * 监听器状态
     */
    @NotNull(message = "监听器状态参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "监听器状态")
    private ListenerStatusEnum listenerStatus;

    /**
     * 监听器描述
     */
    @Schema(description = "监听器描述")
    private String listenerDesc;
}
