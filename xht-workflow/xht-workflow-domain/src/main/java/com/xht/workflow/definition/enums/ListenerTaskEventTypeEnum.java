package com.xht.workflow.definition.enums;

import com.xht.framework.common.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述： 任务监听器事件类型
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum ListenerTaskEventTypeEnum implements XhtEnum<String> {

    /**
     * 当任务已经创建，并且所有任务参数都已经设置时触发。
     */
    CREATE("create", "创建"),
    /**
     * 当任务已经指派给某人时触发。请注意：当流程执行到达用户任务时，在触发create事件之前，会首先触发assignment事件。这顺序看起来不太自然，
     *  但是有实际原因的：当收到create事件时，我们通常希望能看到任务的所有参数，包括办理人。
     */
    ASSIGNMENT("assignment", "指派"),
    /**
     * 当任务已经完成，从运行时数据中删除前触发。
     */
    COMPLETE("complete", "完成"),
    /**
     * 在任务即将被删除前触发。请注意任务由completeTask正常完成时也会触发。
     */
    DELETE("delete", "删除"),
    ;
    /**
     * 枚举值
     */
    private final String value;

    /**
     * 枚举描述
     */
    private final String desc;

}
