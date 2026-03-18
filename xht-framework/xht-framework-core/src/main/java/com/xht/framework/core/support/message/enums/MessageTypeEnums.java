package com.xht.framework.core.support.message.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.core.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 消息类型：1-系统通知 2-业务提醒
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum MessageTypeEnums implements XhtEnum<Integer> {

    SYSTEM_NOTICE(1, "系统通知"),

    BUSINESS_REMIND(2, "业务提醒");
    /**
     * 值
     */
    @JsonValue
    private final Integer value;

    private final String desc;
}
