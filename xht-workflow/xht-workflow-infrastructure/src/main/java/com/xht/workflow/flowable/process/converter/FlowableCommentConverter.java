package com.xht.workflow.flowable.process.converter;

import com.xht.workflow.flowable.process.common.CommentDTO;
import com.xht.workflow.flowable.utils.FlowableDateUtils;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.task.Comment;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 描述： 流程意见转换器
 *
 * @author xht
 **/
@Slf4j
@Component
public class FlowableCommentConverter implements CommentConverter<Comment> {

    /**
     * 将流程意见转换为流程意见DTO
     *
     * @param source 流程意见，非null
     * @return 流程意见DTO，非null
     */
    @Override
    public CommentDTO convert(Comment source) {
        if (Objects.isNull(source)) {
            return null;
        }
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(source.getId());
        commentDTO.setUserId(source.getUserId());
        commentDTO.setTime(FlowableDateUtils.toLocalDateTime(source.getTime()));
        commentDTO.setTaskId(source.getTaskId());
        commentDTO.setProcessInstanceId(source.getProcessInstanceId());
        commentDTO.setType(source.getType());
        commentDTO.setFullMessage(source.getFullMessage());
        return commentDTO;
    }
}
