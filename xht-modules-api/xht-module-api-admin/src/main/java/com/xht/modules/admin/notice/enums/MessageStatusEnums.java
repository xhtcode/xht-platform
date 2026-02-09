package com.xht.modules.admin.notice.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.core.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述 ： 消息状态枚举
 * 1-未读 2-已读 3-已删除（收件人侧）4-已撤回（发件人侧）
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum MessageStatusEnums implements XhtEnum<Integer> {

    UNREAD(1, "未读"),

    READ(2, "已读"),

    REMOVE(3, "已删除"),

    CANCEL(4, "已撤回");

    /**
     * 枚举值
     */
    @JsonValue
    private final Integer value;

    /**
     * 描述
     */
    private final String desc;


}
