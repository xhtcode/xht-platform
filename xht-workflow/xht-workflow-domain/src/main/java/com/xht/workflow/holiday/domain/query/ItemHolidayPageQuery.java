package com.xht.workflow.holiday.domain.query;

import com.xht.framework.common.domain.query.PageBasicQuery;
import com.xht.workflow.holiday.enums.HolidayTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * 流程事项-请假单
 *
 * @author xht
 */
@Data
@Schema(description = "流程事项-请假单查询")
public class ItemHolidayPageQuery extends PageBasicQuery {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 业务id
     */
    @Schema(description = "业务id")
    private Long businessId;

    /**
     * 请假类型 1事假 2病假 3年假 4调休 5婚假 6产假
     */
    @Schema(description = "请假类型")
    private HolidayTypeEnum holidayType;

}
