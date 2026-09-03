package com.xht.workflow.flowable.process.common;

import com.xht.framework.utils.ThrowUtils;
import com.xht.workflow.flowable.common.BpmnBuilder;
import com.xht.workflow.flowable.process.enums.FlowableCommentTypeEnum;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 描述： 流程意见保存参数 构建器
 *
 * @author xht
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentSaveBuilder implements BpmnBuilder<CommentSaveBO> {

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 流程实例id
     */
    private String processInstanceId;

    /**
     * 意见类型
     */
    private String type;

    /**
     * 意见内容
     */
    private String comment;

    /**
     * 构建流程意见保存参数建造者
     *
     * @return 流程意见保存参数建造者
     */
    public static CommentSaveBuilder builder() {
        return new CommentSaveBuilder();
    }

    /**
     * 设置任务id
     *
     * @param taskId 任务id
     * @return 流程意见保存参数建造者
     */
    public CommentSaveBuilder taskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    /**
     * 设置流程实例id
     *
     * @param processInstanceId 流程实例id
     * @return 流程意见保存参数建造者
     */
    public CommentSaveBuilder processInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
        return this;
    }


    /**
     * 设置意见内容
     *
     * @param comment 完整意见内容
     * @return 流程意见保存参数建造者
     */
    public CommentSaveBuilder comment(String comment) {
        return comment(FlowableCommentTypeEnum.COMMENT, comment);
    }

    /**
     * 设置意见内容
     *
     * @param type    意见类型
     * @param comment 完整意见内容
     * @return 流程意见保存参数建造者
     */
    public CommentSaveBuilder comment(FlowableCommentTypeEnum type, String comment) {
        ThrowUtils.notNull(type, "意见类型不能为空");
        return comment(type.getValue(), comment);
    }

    /**
     * 设置意见内容
     *
     * @param type    意见类型
     * @param comment 完整意见内容
     * @return 流程意见保存参数建造者
     */
    public CommentSaveBuilder comment(String type, String comment) {
        ThrowUtils.hasText(type, "意见类型不能为空");
        ThrowUtils.hasText(comment, "意见内容不能为空");
        this.comment = comment;
        this.type = type;
        return this;
    }

    /**
     * 构建流程意见保存参数
     */
    @Override
    public CommentSaveBO build() {
        ThrowUtils.hasText(comment, "意见内容不能为空");
        ThrowUtils.hasText(processInstanceId, "流程实例id不能为空");
        CommentSaveBO commentSaveBO = new CommentSaveBO();
        commentSaveBO.setTaskId(taskId);
        commentSaveBO.setProcessInstanceId(processInstanceId);
        commentSaveBO.setType(type);
        commentSaveBO.setComment(comment);
        return commentSaveBO;
    }
}
