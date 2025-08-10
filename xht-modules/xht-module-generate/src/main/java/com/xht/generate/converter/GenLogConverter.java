package com.xht.generate.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.generate.domain.entity.GenLogEntity;
import com.xht.generate.domain.request.GenLogFormRequest;
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
public interface GenLogConverter extends BasicConverter<GenLogEntity, GenLogFormRequest, GenLogResponse> {

}
