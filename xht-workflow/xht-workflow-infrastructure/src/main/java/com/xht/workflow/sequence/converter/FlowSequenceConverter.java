package com.xht.workflow.sequence.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.workflow.sequence.domain.form.FlowSequenceForm;
import com.xht.workflow.sequence.domain.response.FlowSequenceResponse;
import com.xht.workflow.sequence.entity.FlowSequenceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 描述：流程序列管理
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FlowSequenceConverter extends BasicConverter<FlowSequenceEntity, FlowSequenceForm, FlowSequenceResponse> {
}
