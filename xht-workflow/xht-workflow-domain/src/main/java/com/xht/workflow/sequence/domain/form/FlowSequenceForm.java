package com.xht.workflow.sequence.domain.form;

import com.xht.framework.common.domain.form.BasicForm;
import com.xht.framework.validation.Groups;
import com.xht.workflow.sequence.enums.IsCycleEnums;
import com.xht.workflow.sequence.enums.ResetFlagEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serial;

/**
 * 描述：流程序列管理表单
 *
 * @author xht
 **/
@Data
@Schema(description = "流程序列管理表单")
public class FlowSequenceForm extends BasicForm {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 序列id
     */
    @Null(message = "序列`id`唯一标识必须为空", groups = {Groups.Create.class})
    @NotNull(message = "序列`id`唯一标识参数不合法", groups = {Groups.Update.class})
    @Positive(message = "序列`id`唯一标识参数不合法", groups = {Groups.Update.class})
    @Schema(description = "序列id")
    private Long id;

    /**
     * 序列编码
     */
    @NotEmpty(message = "序列编码参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "序列编码")
    private String sequenceCode;

    /**
     * 序列名称
     */
    @NotEmpty(message = "序列名称参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "序列名称")
    private String sequenceName;

    /**
     * 序列格式 示例：肥料登记的流水号 032000{YYYYMMDD}-{N} YYYYMMDD,当前日期的格式定义，支持YYYY,YY,MM,DD几种格式组合 {N}原值显示当前值 {N6}当前值显示的最小长度为6位，不足时前面补零
     */
    @NotEmpty(message = "序列格式参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "序列格式")
    private String sequenceFormat;

    /**
     * 序列最小值
     */
    @NotNull(message = "序列最小值参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @PositiveOrZero(message = "序列最小值必须为非负数", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "序列最小值")
    private Integer minValue;

    /**
     * 序列最大值
     */
    @NotNull(message = "序列最大值参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @PositiveOrZero(message = "序列最大值必须为非负数", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "序列最大值")
    private Integer maxValue;

    /**
     * 序列当前值
     */
    @NotNull(message = "序列当前值参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @PositiveOrZero(message = "序列当前值必须为非负数", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "序列当前值")
    private Integer currentValue;

    /**
     * 序列步进值
     */
    @NotNull(message = "序列步进值参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @PositiveOrZero(message = "序列步进值必须为非负数", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "序列步进值")
    private Integer steppingValue;

    /**
     * 是否循环
     */
    @NotNull(message = "是否循环参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "是否循环")
    private IsCycleEnums isCycle;

    /**
     * 重置周期 0 不重置 1每天 2月 3年
     */
    @NotNull(message = "重置周期参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "重置周期 0 不重置 1每天 2月 3年")
    private ResetFlagEnums resetFlag;

}
