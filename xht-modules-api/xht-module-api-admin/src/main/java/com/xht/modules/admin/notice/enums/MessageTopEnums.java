package com.xht.modules.admin.notice.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 信息置顶：0-否 1-是
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum MessageTopEnums implements IEnum<Integer> {

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
