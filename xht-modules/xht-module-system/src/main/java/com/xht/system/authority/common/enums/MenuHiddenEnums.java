package com.xht.system.authority.common.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜单隐藏状态枚举类
 * (0显示 1隐藏)
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum MenuHiddenEnums implements IEnum<Integer> {

    /**
     * 显示
     */
    SHOW(0, false),

    /**
     * 隐藏
     */
    HIDE(1, true);


    /**
     * 状态值
     */
    @JsonValue
    private final Integer value;

    private final Boolean hidden;
}
