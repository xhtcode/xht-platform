package com.xht.framework.core.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统内置的标志枚举
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum SystemFlagEnums implements IEnum<Integer> {

    /**
     * 是
     */
    YES(0),

    /**
     * 否
     */
    NO(1);;

    @JsonValue
    private final Integer value;

}
