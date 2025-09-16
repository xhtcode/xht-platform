package com.xht.generate.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.generate.domain.entity.GenDataSourceEntity;
import com.xht.generate.domain.form.GenDataSourceFormRequest;
import com.xht.generate.domain.response.GenDataSourceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 数据源转换器
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GenDataSourceConverter extends BasicConverter<GenDataSourceEntity, GenDataSourceFormRequest, GenDataSourceResponse> {

}
