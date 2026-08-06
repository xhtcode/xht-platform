package com.xht.platform.datasource.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.platform.datasource.domain.form.GenDataSourceForm;
import com.xht.platform.datasource.entity.GenDataSourceEntity;
import com.xht.platform.datasource.domain.response.GenDataSourceResponse;
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
