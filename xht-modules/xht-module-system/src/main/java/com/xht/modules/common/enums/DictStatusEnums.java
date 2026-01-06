package com.xht.modules.common.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 字典状态枚举
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum DictStatusEnums implements IEnum<Integer> {

    /**
     * 禁用
     */
    DISABLE(0),

    /**
     * 启用
     */
    ENABLE(1),

    ;

    /**
     * 字典状态
     */
    @JsonValue
    private final Integer value;

}
