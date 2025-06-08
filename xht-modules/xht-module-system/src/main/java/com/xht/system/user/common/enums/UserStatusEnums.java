package com.xht.system.user.common.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户状态枚举类
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum UserStatusEnums implements IEnum<Integer> {
    // 定义用户状态常量
    /**
     * 正常状态
     */
    NORMAL(0),
    /***
     * 禁用状态
     */
    DISABLE(1),
    /**
     * 锁定状态
     */
    LOCKED(2),
    /**
     * 过期状态
     */
    EXPIRED(3),
    /**
     * 禁止登录状态
     */
    FORBIDDEN(4),
    /**
     * 未注册状态
     */
    UNREGISTER(5),
    /**
     * 其他状态
     */
    OTHER(6),

    ;

    /**
     * 状态值
     */
    @JsonValue
    private final Integer value;

}
