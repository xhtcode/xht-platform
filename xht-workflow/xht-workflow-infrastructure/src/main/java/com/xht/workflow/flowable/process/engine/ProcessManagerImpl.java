package com.xht.workflow.flowable.process.engine;

import com.xht.framework.utils.ThrowUtils;
import com.xht.workflow.common.domain.enums.ProcStartTypeEnum;
import com.xht.workflow.common.exception.WorkFlowException;
import com.xht.workflow.flowable.process.ProcessManager;
import com.xht.workflow.flowable.process.common.CommentDTO;
import com.xht.workflow.flowable.process.common.CommentSaveBO;
import com.xht.workflow.flowable.process.common.ProcessInstanceDTO;
import com.xht.workflow.flowable.process.common.ProcessStartBO;
import com.xht.workflow.flowable.process.converter.FlowableCommentConverter;
import com.xht.workflow.flowable.process.converter.FlowableProcessInstanceConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.task.Comment;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 描述： 流程实例管理器
 *
 * @author xht
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class ProcessManagerImpl implements ProcessManager {

    private final RuntimeService runtimeService;

    private final TaskService taskService;

    private final FlowableProcessInstanceConverter flowableProcessInstanceConverter;

    private final FlowableCommentConverter flowableCommentConverter;

    /**
     * 启动流程实例
     * 根据流程启动类型选择按流程定义id或流程定义key启动
     *
     * @param processStartBO 流程启动参数
     * @return 流程实例DTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProcessInstanceDTO startProcessInstance(ProcessStartBO processStartBO) {
        ThrowUtils.notNull(processStartBO, "流程启动参数不能为空");
        ProcessInstance processInstance;
        if (Objects.equals(processStartBO.getProcStartType(), ProcStartTypeEnum.ID)) {
            processInstance = runtimeService.startProcessInstanceById(processStartBO.getProcStartValue(), processStartBO.getBusinessKey(), processStartBO.getVariables());
        } else if (Objects.equals(processStartBO.getProcStartType(), ProcStartTypeEnum.KEY)) {
            processInstance = runtimeService.startProcessInstanceByKey(processStartBO.getProcStartValue(), processStartBO.getBusinessKey(), processStartBO.getVariables());
        } else {
            throw new WorkFlowException("流程启动方式不合法");
        }
        String comment = processStartBO.getComment();
        if (StringUtils.hasText(comment)) {
            Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).active().list()
                    .stream().findFirst().orElse(null);
            if (Objects.nonNull(task)) {
                taskService.addComment(task.getId(), processInstance.getId(), comment);
            }
        }
        return flowableProcessInstanceConverter.convert(processInstance);
    }


    /**
     * 保存流程实例意见
     *
     * @param commentSaveBO 流程意见业务对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveComment(CommentSaveBO commentSaveBO) {
        ThrowUtils.notNull(commentSaveBO, "流程意见参数不能为空");
        taskService.addComment(commentSaveBO.getTaskId(), commentSaveBO.getProcessInstanceId(), commentSaveBO.getType(), commentSaveBO.getComment());
    }

    /**
     * 删除流程实例意见
     *
     * @param commentId 意见id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeComment(String commentId) {
        ThrowUtils.hasText(commentId, "意见id不能为空");
        taskService.deleteComment(commentId);
    }

    /**
     * 根据任务id查询意见集合
     *
     * @param taskId 任务id
     * @return 意见集合
     */
    @Override
    public List<CommentDTO> findCommentByTaskId(String taskId) {
        ThrowUtils.hasText(taskId, "任务id不能为空");
        List<Comment> taskComments = taskService.getTaskComments(taskId);
        return flowableCommentConverter.convert(taskComments);
    }

    /**
     * 根据意见id查询意见详情
     *
     * @param commentId 意见id
     * @return 意见详情
     */
    @Override
    public CommentDTO findCommentById(String commentId) {
        ThrowUtils.hasText(commentId, "意见id不能为空");
        return flowableCommentConverter.convert(taskService.getComment(commentId));
    }

    /**
     * 根据流程实例id查询意见集合
     *
     * @param processInstanceId 流程实例id
     * @return 意见集合
     */
    @Override
    public List<CommentDTO> findCommentByProcessInstanceId(String processInstanceId) {
        ThrowUtils.hasText(processInstanceId, "流程实例id不能为空");
        List<Comment> processInstanceComments = taskService.getProcessInstanceComments(processInstanceId);
        return flowableCommentConverter.convert(processInstanceComments);
    }

    /**
     * 根据流程实例id查询意见集合
     * 按任务id分组，key为任务id，value为该任务下的意见集合
     *
     * @param processInstanceId 流程实例id
     * @return 按任务id分组的意见集合
     */
    @Override
    public Map<String, List<CommentDTO>> findCommentGroupByTaskId(String processInstanceId) {
        ThrowUtils.hasText(processInstanceId, "流程实例id不能为空");
        List<Comment> processInstanceComments = taskService.getProcessInstanceComments(processInstanceId);
        List<CommentDTO> commentDTOList = flowableCommentConverter.convert(processInstanceComments);
        return commentDTOList.stream().collect(Collectors.groupingBy(CommentDTO::getTaskId));
    }


}
