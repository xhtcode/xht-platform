package com.xht.modules.admin.notice.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.core.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 可见范围(0:否(指定范围);1:是(所有用户可见))
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum NoticeAllVisibleEnums implements XhtEnum<Integer> {
    NO(0, "否"),
    YES(1, "是");
    @JsonValue
    private final Integer value;
    private final String desc;
}
