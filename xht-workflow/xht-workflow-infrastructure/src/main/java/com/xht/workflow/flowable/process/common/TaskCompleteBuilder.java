package com.xht.workflow.flowable.process.common;

import com.xht.framework.utils.ThrowUtils;
import com.xht.workflow.flowable.common.BpmnBuilder;
import com.xht.workflow.flowable.process.enums.FlowableCommentTypeEnum;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述： 任务完成参数 构建器
 *
 * @author xht
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TaskCompleteBuilder implements BpmnBuilder<TaskCompleteBO> {

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 当前办理人id
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
    private final Map<String, Object> variables = new HashMap<>();

    /**
     * 构建任务完成参数建造者
     *
     * @return 任务完成参数建造者
     */
    public static TaskCompleteBuilder builder() {
        return new TaskCompleteBuilder();
    }

    /**
     * 设置任务id
     *
     * @param taskId 任务id
     * @return 任务完成参数建造者
     */
    public TaskCompleteBuilder taskId(String taskId) {
        ThrowUtils.hasText(taskId, "任务id不能为空");
        this.taskId = taskId;
        return this;
    }

    /**
     * 设置当前办理人id
     *
     * @param userId 当前办理人id，可空
     * @return 任务完成参数建造者
     */
    public TaskCompleteBuilder userId(String userId) {
        this.userId = userId;
        return this;
    }

    /**
     * 设置意见内容
     *
     * @param comment 完整意见内容
     * @return 任务完成参数建造者
     */
    public TaskCompleteBuilder comment(String comment) {
        return comment(FlowableCommentTypeEnum.COMMENT, comment);
    }

    /**
     * 设置任务完成意见内容
     *
     * @param type    任务完成意见类型
     * @param comment 完整任务完成意见内容
     * @return 任务完成参数建造者
     */
    public TaskCompleteBuilder comment(FlowableCommentTypeEnum type, String comment) {
        ThrowUtils.notNull(type, "任务完成意见类型不能为空");
        return comment(type.getValue(), comment);
    }

    /**
     * 设置任务完成意见内容
     *
     * @param type    任务完成意见类型
     * @param comment 完整任务完成意见内容
     * @return 任务完成参数建造者
     */
    public TaskCompleteBuilder comment(String type, String comment) {
        ThrowUtils.hasText(type, "任务完成意见类型不能为空");
        ThrowUtils.hasText(comment, "任务完成意见内容不能为空");
        this.comment = comment;
        this.type = type;
        return this;
    }


    /**
     * 添加单个流程变量
     *
     * @param key   流程变量key
     * @param value 流程变量值
     * @return 任务完成参数建造者
     */
    public TaskCompleteBuilder variable(String key, Object value) {
        ThrowUtils.hasText(key, "流程变量key不能为空");
        this.variables.put(key, value);
        return this;
    }

    /**
     * 批量添加流程变量
     *
     * @param variables 流程变量
     * @return 任务完成参数建造者
     */
    public TaskCompleteBuilder variables(Map<String, Object> variables) {
        if (CollectionUtils.isEmpty(variables)) {
            return this;
        }
        this.variables.putAll(variables);
        return this;
    }

    /**
     * 删除流程变量
     *
     * @param key 流程变量key
     * @return 任务完成参数建造者
     */
    public TaskCompleteBuilder removeVariable(String key) {
        this.variables.remove(key);
        return this;
    }

    /**
     * 构建任务完成参数
     */
    @Override
    public TaskCompleteBO build() {
        ThrowUtils.hasText(taskId, "任务id不能为空");
        TaskCompleteBO taskCompleteBO = new TaskCompleteBO();
        taskCompleteBO.setTaskId(taskId);
        taskCompleteBO.setUserId(userId);
        taskCompleteBO.setComment(comment);
        taskCompleteBO.setVariables(variables);
        return taskCompleteBO;
    }
}
