package com.xht.modules.admin.notice.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 信息收藏：0-否 1-是
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum MessageStarEnums implements IEnum<Integer> {

    NO(0, "否"),

    YES(1, "是");

    /**
     * 值
     */
    @JsonValue
    private final Integer value;

    /**
     * 描述
     */
    private final String desc;

}
