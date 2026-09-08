package com.xht.workflow.holiday.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.NoneDeleteEntity;
import com.xht.workflow.holiday.enums.HolidayTypeEnum;
import lombok.Data;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 流程事项-请假单
 *
 * @author xht
 */
@Data
@TableName(value = "xht_item_holiday")
public class ItemHolidayEntity extends NoneDeleteEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 业务id
     */
    @TableField(value = "business_id")
    private Long businessId;

    /**
     * 请假类型 1事假 2病假 3年假 4调休 5婚假 6产假
     */
    @TableField(value = "holiday_type")
    private HolidayTypeEnum holidayType;

    /**
     * 请假开始时间
     */
    @TableField(value = "holiday_start_time")
    private LocalDateTime holidayStartTime;

    /**
     * 请假结束时间
     */
    @TableField(value = "holiday_end_time")
    private LocalDateTime holidayEndTime;

    /**
     * 请假天数
     */
    @TableField(value = "holiday_days")
    private String holidayDays;

    /**
     * 请假原因
     */
    @TableField(value = "holiday_reason")
    private String holidayReason;

    /**
     * 备注
     */
    @TableField(value = "holiday_remark")
    private String holidayRemark;

}
