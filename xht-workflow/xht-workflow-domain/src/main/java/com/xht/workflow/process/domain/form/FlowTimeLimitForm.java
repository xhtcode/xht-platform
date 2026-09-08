package com.xht.workflow.process.domain.form;

import com.xht.framework.validation.Groups;
import com.xht.workflow.common.domain.form.WorkFlowForm;
import com.xht.workflow.process.enums.FlowTimeLimitTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 流程扩展-流程时限
 *
 * @author xht
 */
@Data
@Schema(description = "流程扩展-流程时限表单")
public class FlowTimeLimitForm extends WorkFlowForm {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 业务id
     */
    @NotNull(message = "业务id参数不合法", groups = {Groups.Create.class})
    @Schema(description = "业务id")
    private Long businessId;

    /**
     * 时限类型：natural自然日  workday工作日(跳过周末顺延)
     */
    @NotNull(message = "时限类型参数不合法", groups = {Groups.Create.class})
    @Schema(description = "时限类型")
    private FlowTimeLimitTypeEnum timeLimitType;

    /**
     * 时限天数
     */
    @NotNull(message = "时限天数参数不合法", groups = {Groups.Create.class})
    @Positive(message = "时限天数必须为正数", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "时限天数")
    private Integer timeLimitValue;

    /**
     * 时限开始时间(为空则从创建时刻起算)
     */
    @Schema(description = "时限开始时间")
    private LocalDateTime startTime;

    /**
     * 临期预警提前量(天)
     */
    @Positive(message = "临期预警提前量必须为正数", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "临期预警提前量(天)")
    private Integer warnAhead;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

}
