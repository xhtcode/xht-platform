package com.xht.workflow.flowable.process.common;

import com.xht.workflow.flowable.common.bo.BpmnBO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 描述： 流程意见业务对象
 *
 * @author xht
 **/
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentSaveBO extends BpmnBO {

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

}
