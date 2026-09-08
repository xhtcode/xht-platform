package com.xht.workflow.process.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.workflow.process.domain.form.FlowBusinessForm;
import com.xht.workflow.process.domain.response.FlowBusinessResponse;
import com.xht.workflow.process.entity.FlowBusinessEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 描述：流程扩展-流程业务
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FlowBusinessConverter extends BasicConverter<FlowBusinessEntity, FlowBusinessForm, FlowBusinessResponse> {

}
