package com.xht.workflow.flowable.process.converter;

import com.xht.workflow.flowable.common.converter.WorkFlowConverter;
import com.xht.workflow.flowable.process.common.ProcessInstanceDTO;

/**
 * 描述： 流程实例转换器
 *
 * @author xht
 **/
public interface ProcessInstanceConverter<T> extends WorkFlowConverter<T, ProcessInstanceDTO> {
}
