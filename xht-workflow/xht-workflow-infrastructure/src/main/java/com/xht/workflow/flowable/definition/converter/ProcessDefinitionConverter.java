package com.xht.workflow.flowable.definition.converter;

import com.xht.workflow.flowable.common.converter.WorkFlowConverter;
import com.xht.workflow.flowable.definition.common.ProcessDefinitionDTO;

/**
 * 描述： 部署转换器
 *
 * @author xht
 **/
public interface ProcessDefinitionConverter<T> extends WorkFlowConverter<T, ProcessDefinitionDTO> {
}
