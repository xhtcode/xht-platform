package com.xht.generate.converter;

import com.xht.framework.core.converter.IConverter;
import com.xht.generate.domain.entity.GenLogEntity;
import com.xht.generate.domain.response.GenLogResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 生成日志转换器
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GenLogConverter extends IConverter<GenLogEntity, GenLogResponse> {

}
