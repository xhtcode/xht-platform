package com.xht.workflow.definition.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.workflow.definition.domain.form.FlowListenerFieldForm;
import com.xht.workflow.definition.domain.response.FlowListenerFieldResponse;
import com.xht.workflow.definition.entity.FlowListenerFieldEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 描述： 流程扩展-执行监听器（字段管理）转换器
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FlowListenerFieldConverter extends BasicConverter<FlowListenerFieldEntity, FlowListenerFieldForm, FlowListenerFieldResponse> {

}