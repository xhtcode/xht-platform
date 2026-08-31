package com.xht.workflow.definition.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.workflow.definition.domain.form.FlowFormForm;
import com.xht.workflow.definition.domain.response.FlowFormResponse;
import com.xht.workflow.definition.entity.FlowFormEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 描述：流程扩展-流程表单
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FlowFormConverter extends BasicConverter<FlowFormEntity, FlowFormForm, FlowFormResponse> {

}
