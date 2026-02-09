package com.xht.modules.admin.notice.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.core.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 信息置顶：0-否 1-是
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum MessageTopEnums implements XhtEnum<Integer> {

    NO(0, "否"),

    YES(1, "是");

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
