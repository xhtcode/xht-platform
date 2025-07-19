package com.xht.system.modules.authority.common.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 角色状态枚举类
 * （0正常 1停用）
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum RoleStatusEnums implements IEnum<Integer> {

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
