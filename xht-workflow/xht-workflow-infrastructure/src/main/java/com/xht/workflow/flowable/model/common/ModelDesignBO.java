package com.xht.workflow.flowable.model.common;

import com.xht.workflow.flowable.common.bo.BpmnBO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * 描述： 模型设计业务对象
 *
 * @author xht
 **/
@Getter
@Setter(AccessLevel.PROTECTED)
public class ModelDesignBO extends BpmnBO {

    /**
     * 模型ID
     */
    private String modelId;

    /**
     * BPMN xml
     */
    private String bpmnXml;

    /**
     * 是否新版本
     */
    private Boolean newVersion;

}
