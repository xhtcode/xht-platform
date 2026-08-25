package com.xht.workflow.flowable.deploy.converter;

import com.xht.workflow.flowable.common.converter.WorkFlowConverter;
import com.xht.workflow.flowable.deploy.common.ProcessDefinitionDTO;

/**
 * 描述： 部署转换器
 *
 * @author xht
 **/
public interface DeployConverter<T> extends WorkFlowConverter<T, ProcessDefinitionDTO> {
}
