package com.xht.system.modules.authority.common.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜单状态枚举
 * （0正常 1停用）
 *
 * @author xht
 **/
@SuppressWarnings("unused")
@Getter
@AllArgsConstructor
public enum MenuStatusEnums implements IEnum<Integer> {
    /**
     * 正常
     */
    NORMAL(0),

    /**
     * 停用
     */
    DISABLE(1);

    @JsonValue
    private final Integer value;
}
