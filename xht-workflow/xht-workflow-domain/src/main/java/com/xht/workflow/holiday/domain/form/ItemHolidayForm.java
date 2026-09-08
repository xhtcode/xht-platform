package com.xht.workflow.holiday.domain.form;

import com.xht.framework.validation.Groups;
import com.xht.workflow.common.domain.form.WorkFlowForm;
import com.xht.workflow.holiday.enums.HolidayTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 流程事项-请假单
 *
 * @author xht
 */
@Data
@Schema(description = "流程事项-请假单表单")
public class ItemHolidayForm extends WorkFlowForm {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 业务id
     */
    @NotNull(message = "业务id参数不合法", groups = {Groups.Create.class})
    @Schema(description = "业务id")
    private Long businessId;

    /**
     * 请假类型 1事假 2病假 3年假 4调休 5婚假 6产假
     */
    @NotNull(message = "请假类型参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "请假类型")
    private HolidayTypeEnum holidayType;

    /**
     * 请假开始时间
     */
    @NotNull(message = "请假开始时间参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "请假开始时间")
    private LocalDateTime holidayStartTime;

    /**
     * 请假结束时间
     */
    @NotNull(message = "请假结束时间参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "请假结束时间")
    private LocalDateTime holidayEndTime;

    /**
     * 请假天数
     */
    @Schema(description = "请假天数")
    private String holidayDays;

    /**
     * 请假原因
     */
    @Schema(description = "请假原因")
    private String holidayReason;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String holidayRemark;

}
