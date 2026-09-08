package com.xht.workflow.process.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.workflow.process.domain.form.FlowTimeLimitForm;
import com.xht.workflow.process.domain.response.FlowTimeLimitResponse;
import com.xht.workflow.process.entity.FlowTimeLimitEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 描述：流程扩展-流程时限
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FlowTimeLimitConverter extends BasicConverter<FlowTimeLimitEntity, FlowTimeLimitForm, FlowTimeLimitResponse> {

}
