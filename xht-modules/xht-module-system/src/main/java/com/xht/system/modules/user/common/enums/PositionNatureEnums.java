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
    FORMAL(0, "正式岗位", "长期稳定的核心岗位，属于正式任命的职位，在劳动合同中有明确约定，是个人的核心职业身份，承担主要工作职责和KPI指标"),

    // 临时岗位：因短期需求设立的过渡性岗位
    TEMPORARY(1, "临时岗位", "因短期需求（如项目攻坚、人员空缺补位等）设立的过渡性岗位，期限通常不超过6个月，任务结束后岗位即终止"),

    // 代理岗位：临时代理他人职责的岗位，有明确代理期限
    AGENT(2, "代理岗位", "因原岗位人员暂离（如休假、出差、离职交接）而临时代理其职责的岗位，有明确的代理起止时间，代理期结束后回归原岗位"),

    // 兼职岗位：非全日制的弹性岗位
    PART_TIME(3, "兼职岗位", "非全日制的弹性岗位，通常按工作量或时间计酬，不占用正式编制，仅承担特定专项任务或辅助性工作"),

    // 实习岗位：面向在校学生的实践性岗位
    INTERN(4, "实习岗位", "面向在校学生的实践性岗位，以培养技能和积累经验为目的，有明确的实习期限（通常3-6个月），签订实习协议而非劳动合同");

    /**
     * 岗位性质值
     */
    @JsonValue
    private final Integer value;

    /**
     * 岗位性质名称
     */
    private final String name;

    /**
     * 岗位性质描述
     */
    private final String desc;


}
    