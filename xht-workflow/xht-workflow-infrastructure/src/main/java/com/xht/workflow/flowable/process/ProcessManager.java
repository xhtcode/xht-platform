package com.xht.workflow.flowable.process;

import com.xht.workflow.flowable.process.common.*;

import java.util.List;
import java.util.Map;

/**
 * 描述： 流程实例管理器
 *
 * @author xht
 **/
public interface ProcessManager {

    /**
     * 启动流程实例
     * 根据流程启动类型选择按流程定义id或流程定义key启动
     *
     * @param processStartBO 流程启动参数
     * @return 流程实例DTO
     */
    ProcessInstanceDTO startProcessInstance(ProcessStartBO processStartBO);

    /**
     * 完成任务
     * 按需认领任务并记录完成意见，携带流程变量完成任务，流程流转至下一节点
     *
     * @param taskCompleteBO 任务完成参数
     */
    void taskComplete(TaskCompleteBO taskCompleteBO);

    /**
     * 保存流程实例意见
     *
     * @param commentSaveBO 流程意见业务对象
     */
    void saveComment(CommentSaveBO commentSaveBO);

    /**
     * 删除流程实例意见
     *
     * @param commentId 意见id
     */
    void removeComment(String commentId);

    /**
     * 根据意见id查询意见详情
     *
     * @param commentId 意见id
     * @return 意见详情
     */
    CommentDTO findCommentById(String commentId);

    /**
     * 根据任务id查询意见集合
     *
     * @param taskId 任务id
     * @return 意见集合
     */
    List<CommentDTO> findCommentByTaskId(String taskId);

    /**
     * 根据流程实例id查询意见集合
     * 按任务id分组，key为任务id，value为该任务下的意见集合
     *
     * @param processInstanceId 流程实例id
     * @return 按任务id分组的意见集合
     */
    Map<String, List<CommentDTO>> findCommentProcessInstanceId(String processInstanceId);

    /**
     * 根据流程实例id查询历史任务集合
     * 按任务开始时间升序排列
     *
     * @param processInstanceId 流程实例id
     * @return 历史任务集合
     */
    List<HistoricTaskDTO> findHistoricTaskByProcessInstanceId(String processInstanceId);

}
