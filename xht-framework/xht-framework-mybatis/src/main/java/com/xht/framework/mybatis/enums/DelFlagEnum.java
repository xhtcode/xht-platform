package com.xht.framework.mybatis.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述 ：删除标识
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum DelFlagEnum implements IEnum<Integer> {

    /**
     * 表示未删除的状态
     */
    NORMAL(0, "未删除"),

    /**
     * 表示已删除的状态
     */
    DELETE(1, "已删除");

    /**
     * 枚举值的JSON表示
     */
    @EnumValue
    private final Integer value;

    /**
     * 枚举值的描述信息
     */
    private final String desc;


}
