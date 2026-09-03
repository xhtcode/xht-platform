package com.xht.workflow.flowable.process.common;

import com.xht.framework.utils.StringUtils;
import com.xht.workflow.flowable.common.bo.BpmnBO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * 描述： 任务完成参数
 *
 * @author xht
 **/
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaskCompleteBO extends BpmnBO {

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 当前办理人id，可空；任务未分配办理人时由该用户认领，任务已分配他人办理时禁止办理
     */
    private String userId;

    /**
     * 任务完成意见类型
     */
    private String type;

    /**
     * 任务完成意见
     */
    private String comment;

    /**
     * 流程变量
     */
    private Map<String, Object> variables;

    /**
     * 判断是否有意见
     *
     * @return 是否有意见
     */
    public boolean hasComment() {
        return StringUtils.hasText(comment);
    }
}
