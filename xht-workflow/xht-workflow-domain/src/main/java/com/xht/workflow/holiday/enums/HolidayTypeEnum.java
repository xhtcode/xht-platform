package com.xht.workflow.holiday.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.common.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述：请假类型
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum HolidayTypeEnum implements XhtEnum<String> {

    /**
     * 事假
     */
    PERSONAL_LEAVE("1", "事假"),

    /**
     * 病假
     */
    SICK_LEAVE("2", "病假"),

    /**
     * 年假
     */
    ANNUAL_LEAVE("3", "年假"),

    /**
     * 调休
     */
    COMPENSATORY_LEAVE("4", "调休"),

    /**
     * 婚假
     */
    MARRIAGE_LEAVE("5", "婚假"),

    /**
     * 产假
     */
    MATERNITY_LEAVE("6", "产假");

    @JsonValue
    private final String value;

    private final String desc;

}
