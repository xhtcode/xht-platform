package com.xht.workflow.sequence.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.common.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述：重置周期 0 不重置 1每天 2月 3年
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum ResetFlagEnums implements XhtEnum<Integer> {

    RESET_NONE(0, "不重置"),

    RESET_DAY(1, "每天"),

    RESET_MONTH(2, "每月"),

    RESET_YEAR(3, "每年");

    @JsonValue
    private final Integer value;

    private final String desc;

}
