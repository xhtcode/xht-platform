package com.xht.workflow.flowable.process.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.common.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Flowable ACT_HI_COMMENT TYPE_ 意见类型枚举
 *
 * @author xht
 */
@Getter
@AllArgsConstructor
public enum FlowableCommentTypeEnum implements XhtEnum<String> {

    /**
     * 普通审批意见，默认，人工审批留言（同意/同意并转审等通用）
     * taskId：绑定对应任务ID
     */
    COMMENT("comment", "普通审批意见"),

    /**
     * 发起人提交申请意见
     * taskId：null(实例级别) 或者 第一个userTask任务ID(绑定首节点)
     */
    START_SUBMIT("start_submit", "发起人提交意见"),

    /**
     * 驳回意见
     * taskId：当前审批任务ID
     */
    REJECT("reject", "驳回意见"),

    /**
     * 退回（回退上一节点）意见
     */
    ROLL_BACK("roll_back", "退回意见"),

    /**
     * 委派意见：A委派给B处理
     */
    DELEGATE("delegate", "委派意见"),

    /**
     * 转办意见：A转给B全权处理
     */
    TRANSFER("transfer", "转办意见"),

    /**
     * 会签/或签 投票意见
     */
    COUNTER_SIGN("countersign", "会签意见"),

    /**
     * 传阅意见
     */
    CIRCULATE("circulate", "传阅意见"),

    /**
     * 撤销申请意见（发起人撤销流程）
     * taskId：null，流程实例级别
     */
    CANCEL("cancel", "撤销申请"),

    /**
     * 终止流程意见
     * taskId：null，流程实例级别
     */
    TERMINATE("terminate", "终止流程"),

    /**
     * 系统自动生成意见（监听器、定时任务、自动节点）
     * taskId可为null或任务ID
     */
    SYSTEM("system", "系统自动意见");


    @JsonValue
    private final String value;

    private final String desc;


}
