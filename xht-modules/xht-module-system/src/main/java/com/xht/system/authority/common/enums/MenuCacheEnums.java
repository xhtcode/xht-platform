package com.xht.system.authority.common.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜单缓存枚举类
 * （0是 1否）
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum MenuCacheEnums implements IEnum<Integer> {

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
