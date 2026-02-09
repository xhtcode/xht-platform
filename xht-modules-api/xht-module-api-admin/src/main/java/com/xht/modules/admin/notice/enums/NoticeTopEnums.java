package com.xht.modules.admin.notice.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.core.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 是否置顶(0:否;1:是)
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum NoticeTopEnums implements XhtEnum<Integer> {
    NO(0, "否"),
    YES(1, "是");
    @JsonValue
    private final Integer value;
    private final String desc;
}
