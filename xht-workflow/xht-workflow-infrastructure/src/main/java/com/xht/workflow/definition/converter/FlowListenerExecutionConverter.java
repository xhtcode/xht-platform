package com.xht.workflow.definition.converter;

import com.xht.workflow.definition.domain.form.FlowListenerExecutionBasicForm;
import com.xht.workflow.definition.domain.response.FlowListenerExecutionResponse;
import com.xht.workflow.definition.entity.FlowListenerExecutionEntity;
import com.xht.framework.mybatis.converter.BasicConverter;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 流程扩展-执行监听器 转换器
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FlowListenerExecutionConverter extends BasicConverter<FlowListenerExecutionEntity, FlowListenerExecutionBasicForm, FlowListenerExecutionResponse> {

}
