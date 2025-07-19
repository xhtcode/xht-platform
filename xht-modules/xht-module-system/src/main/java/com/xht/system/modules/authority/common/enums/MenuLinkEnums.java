package com.xht.system.modules.authority.common.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜单链接是否是外联
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum MenuLinkEnums implements IEnum<Integer> {

    /**
     * 是
     */
    YES(0, true),

    /**
     * 否
     */
    NO(1, false);


    @JsonValue
    private final Integer value;

    private final Boolean status;
}
