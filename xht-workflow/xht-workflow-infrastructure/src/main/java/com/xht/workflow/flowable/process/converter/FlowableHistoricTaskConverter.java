package com.xht.workflow.flowable.process.converter;

import com.xht.workflow.flowable.process.common.HistoricTaskDTO;
import com.xht.workflow.flowable.utils.FlowableDateUtils;
import lombok.extern.slf4j.Slf4j;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 描述： 历史任务转换器
 *
 * @author xht
 **/
@Slf4j
@Component
public class FlowableHistoricTaskConverter implements HistoricTaskConverter<HistoricTaskInstance> {

    /**
     * 将历史任务转换为历史任务DTO
     *
     * @param source 历史任务，非null
     * @return 历史任务DTO，非null
     */
    @Override
    public HistoricTaskDTO convert(HistoricTaskInstance source) {
        if (Objects.isNull(source)) {
            return null;
        }
        HistoricTaskDTO historicTaskDTO = new HistoricTaskDTO();
        historicTaskDTO.setTaskId(source.getId());
        historicTaskDTO.setName(source.getName());
        historicTaskDTO.setTaskDefinitionKey(source.getTaskDefinitionKey());
        historicTaskDTO.setProcessInstanceId(source.getProcessInstanceId());
        historicTaskDTO.setProcessDefinitionId(source.getProcessDefinitionId());
        historicTaskDTO.setExecutionId(source.getExecutionId());
        historicTaskDTO.setAssignee(source.getAssignee());
        historicTaskDTO.setOwner(source.getOwner());
        historicTaskDTO.setStartTime(FlowableDateUtils.toLocalDateTime(source.getStartTime()));
        historicTaskDTO.setEndTime(FlowableDateUtils.toLocalDateTime(source.getEndTime()));
        historicTaskDTO.setDurationInMillis(source.getDurationInMillis());
        historicTaskDTO.setDeleteReason(source.getDeleteReason());
        historicTaskDTO.setClaimTime(FlowableDateUtils.toLocalDateTime(source.getClaimTime()));
        historicTaskDTO.setDueDate(FlowableDateUtils.toLocalDateTime(source.getDueDate()));
        historicTaskDTO.setPriority(source.getPriority());
        return historicTaskDTO;
    }
}
