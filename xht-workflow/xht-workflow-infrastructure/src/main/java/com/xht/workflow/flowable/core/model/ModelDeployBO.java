package com.xht.workflow.flowable.core.model;

import com.xht.workflow.flowable.core.BpmnBO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * 描述： 模型部署入参
 *
 * @author xht
 **/
@Getter
@Setter(AccessLevel.PROTECTED)
public class ModelDeployBO extends BpmnBO {

    /**
     * 模型ID
     */
    protected String modelId;


}
