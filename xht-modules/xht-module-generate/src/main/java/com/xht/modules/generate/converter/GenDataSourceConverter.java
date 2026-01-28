package com.xht.modules.generate.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.modules.generate.domain.form.GenDataSourceForm;
import com.xht.modules.generate.domain.response.GenDataSourceResponse;
import com.xht.modules.generate.entity.GenDataSourceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 数据源转换器
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GenDataSourceConverter extends BasicConverter<GenDataSourceEntity, GenDataSourceForm, GenDataSourceResponse> {

}
