package com.xht.modules.common.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 子节点是否显示禁用状态
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum DictShowDisabledEnums implements IEnum<Integer> {

    /**
     * 禁用
     */
    DISABLED(0),

    /**
     * 启用
     */
    SHOW(1),
    ;
    /**
     * 字典状态
     */
    @JsonValue
    private final Integer value;
}
