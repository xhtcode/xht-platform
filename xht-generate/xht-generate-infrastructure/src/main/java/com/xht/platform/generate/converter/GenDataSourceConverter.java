package com.xht.platform.generate.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.platform.form.GenDataSourceForm;
import com.xht.platform.generate.entity.GenDataSourceEntity;
import com.xht.platform.response.GenDataSourceResponse;
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
