package com.xht.workflow.flowable.model.common;

import com.xht.workflow.flowable.common.bo.BpmnBO;
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
