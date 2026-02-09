package com.xht.modules.admin.notice.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.core.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通知状态(0:未发布;1:已发布;2:已下架;3:已过期)
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum NoticeStatusEnums implements XhtEnum<Integer> {
    NOT_PUBLISH(0, "未发布"),
    PUBLISH(1, "已发布"),
    UNDER_SHELVE(2, "已下架"),
    EXPIRED(3, "已过期");
    @JsonValue
    private final Integer value;
    private final String desc;
}
