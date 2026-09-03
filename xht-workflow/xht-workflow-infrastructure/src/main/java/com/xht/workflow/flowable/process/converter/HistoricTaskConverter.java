package com.xht.workflow.flowable.process.converter;

import com.xht.workflow.flowable.common.converter.WorkFlowConverter;
import com.xht.workflow.flowable.process.common.HistoricTaskDTO;
import org.flowable.task.api.history.HistoricTaskInstance;

/**
 * 描述： 历史任务转换器
 *
 * @author xht
 **/
public interface HistoricTaskConverter<T> extends WorkFlowConverter<T, HistoricTaskDTO> {
}
