package com.xht.modules.admin.notice.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.core.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通知类型状态(0:未启用1:启用)
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum NoticeTypeStatusEnums implements XhtEnum<Integer> {

    NOT_ENABLE(0, "未启用"),

    ENABLE(1, "启用");

    @JsonValue
    private final Integer value;
    private final String desc;
}
