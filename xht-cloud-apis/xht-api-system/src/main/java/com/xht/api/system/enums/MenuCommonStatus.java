package com.xht.api.system.enums;

/**
 * 菜单通用状态枚举类
 *
 * @author xht
 **/

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MenuCommonStatus implements IEnum<Integer> {

    /**
     * 否
     */
    NO(0, false),

    /**
     * 是
     */
    YES(1, true),

    ;

    @JsonValue
    private final Integer value;

    private final Boolean status;
}
