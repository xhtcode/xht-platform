package com.xht.modules.admin.notice.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 是否定时发布(0:否(立即发布);1:是(按发布时间生效))
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum NoticeTimedPublishEnums implements IEnum<Integer> {
    NOT_PUBLISH(0, "否(立即发布)"),
    PUBLISH(1, "是(按发布时间生效)");
    @JsonValue
    private final Integer value;
    private final String desc;

}
