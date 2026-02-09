package com.xht.modules.admin.notice.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.core.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 是否定时发布(0:否(立即发布);1:是(按发布时间生效))
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum NoticeTimedPublishEnums implements XhtEnum<Integer> {
    NOT_PUBLISH(0, "否(立即发布)"),
    PUBLISH(1, "是(按发布时间生效)");
    @JsonValue
    private final Integer value;
    private final String desc;

}
