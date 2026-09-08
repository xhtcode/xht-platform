package com.xht.workflow.flowable.process.common;

import com.xht.workflow.flowable.common.dto.BpmnDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * 描述： 任务节点DTO
 *
 * @author xht
 **/
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HighlightNodeDTO extends BpmnDTO {

    /**
     * 已完成节点
     */
    private Collection<String> finishedNodes;

    /**
     * 已完成连线
     */
    private Collection<String> finishedLines;

    /**
     * 待办任务
     */
    private Collection<String> unfinishedTasks;

    /**
     * 已驳回任务
     */
    private Collection<String> rejectedTasks;

}
