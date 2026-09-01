package com.xht.workflow.definition.domain.form;

import com.xht.framework.validation.Groups;
import com.xht.workflow.common.domain.form.WorkFlowForm;
import com.xht.framework.common.enums.DefaultStatusEnum;
import com.xht.framework.common.enums.EnableStatusEnum;
import com.xht.workflow.common.domain.enums.ProcStartTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;

/**
 * 流程扩展-流程定义
 *
 * @author xht
 */
@Data
@Schema(description = "流程扩展-流程定义表单")
public class FlowItemProcessForm extends WorkFlowForm {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 事项定义主键id
     */
    @NotNull(message = "事项定义主键id参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "事项定义主键id")
    private Long itemDefId;

    /**
     * 路由主键
     */
    @NotEmpty(message = "路由主键参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "路由主键")
    private String routerKey;

    /**
     * 路由名称
     */
    @NotEmpty(message = "路由名称参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "路由名称")
    private String routerName;

    /**
     * 流程启动方式id或者key
     */
    @NotNull(message = "流程启动方式id或者key参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "流程启动方式id或者key")
    private ProcStartTypeEnum procStartType;

    /**
     * 流程定义id
     */
    @Schema(description = "流程定义id，根据启动方式自动填充")
    private String procDefId;

    /**
     * 流程定义key
     */
    @Schema(description = "流程定义key，根据启动方式自动填充")
    private String procDefKey;

    /**
     * 匹配优先级
     */
    @Schema(description = "匹配优先级")
    private Integer procDefPriority;

    /**
     * 默认状态
     */
    @NotNull(message = "默认状态参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "默认状态")
    private DefaultStatusEnum defaultStatus;

    /**
     * 启用状态
     */
    @NotNull(message = "启用状态参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "启用状态")
    private EnableStatusEnum enableStatus;

    /**
     * 备注
     */
    @NotEmpty(message = "备注参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "备注")
    private String remark;

}
