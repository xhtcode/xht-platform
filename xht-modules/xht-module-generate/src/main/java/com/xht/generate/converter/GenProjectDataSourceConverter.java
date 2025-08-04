package com.xht.generate.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.generate.domain.entity.GenProjectDataSourceEntity;
import com.xht.generate.domain.request.GenProjectDataSourceFormRequest;
import com.xht.generate.domain.response.GenProjectDataSourceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 项目数据源转换器
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GenProjectDataSourceConverter extends BasicConverter<GenProjectDataSourceEntity, GenProjectDataSourceFormRequest, GenProjectDataSourceResponse> {




                        }
