package com.xht.workflow.definition.converter;

import com.xht.workflow.definition.domain.form.FlowListenerTaskBasicForm;
import com.xht.workflow.definition.domain.response.FlowListenerTaskResponse;
import com.xht.workflow.definition.entity.FlowListenerTaskEntity;
import com.xht.framework.mybatis.converter.BasicConverter;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 流程扩展-任务监听器转换器
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FlowListenerTaskConverter extends BasicConverter<FlowListenerTaskEntity, FlowListenerTaskBasicForm, FlowListenerTaskResponse> {

}
