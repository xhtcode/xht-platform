package com.xht.workflow.flowable.model.converter;

import com.xht.workflow.flowable.common.converter.WorkFlowConverter;
import com.xht.workflow.flowable.model.common.ModelDTO;

/**
 * 描述： 流程模型转换器
 *
 * @author xht
 **/
public interface ModelConverter<T> extends WorkFlowConverter<T, ModelDTO> {

}
