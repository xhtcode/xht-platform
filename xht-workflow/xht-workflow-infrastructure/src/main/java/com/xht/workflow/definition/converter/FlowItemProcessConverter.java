package com.xht.workflow.definition.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.workflow.definition.domain.form.FlowItemProcessForm;
import com.xht.workflow.definition.domain.response.FlowItemProcessResponse;
import com.xht.workflow.definition.entity.FlowItemProcessEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 描述：流程扩展-流程定义
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FlowItemProcessConverter extends BasicConverter<FlowItemProcessEntity, FlowItemProcessForm, FlowItemProcessResponse> {

}
