package com.xht.system.modules.user.common.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 岗位性质枚举类，区分不同类型的岗位性质
 *
 * @author xht
 */
@Getter
@AllArgsConstructor
public enum PositionNatureEnums implements IEnum<Integer> {

    // 正式岗位：长期稳定的核心岗位，有正式任命和劳动合同约定
    FORMAL(0, "正式岗位"),

    // 临时岗位：因短期需求设立的过渡性岗位
    TEMPORARY(1, "临时岗位"),

    // 代理岗位：临时代理他人职责的岗位，有明确代理期限
    AGENT(2, "代理岗位"),

    // 兼职岗位：非全日制的弹性岗位
    PART_TIME(3, "兼职岗位"),

    // 实习岗位：面向在校学生的实践性岗位
    INTERN(4, "实习岗位");

    /**
     * 岗位性质值
     */
    @JsonValue
    private final Integer value;

    /**
     * 岗位性质名称
     */
    private final String name;


}
    