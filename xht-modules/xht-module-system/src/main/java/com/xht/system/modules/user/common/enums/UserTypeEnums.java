package com.xht.system.modules.user.common.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户类型枚举
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum UserTypeEnums implements IEnum<Integer> {
    /**
     * 管理员用户
     */
    ADMIN(1, "管理员用户"),
    /**
     * 商家用户
     */
    BUSINESS(2, "商家用户"),

    ;
    private final Integer value;

    private final String desc;

}
