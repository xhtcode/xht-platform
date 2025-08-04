package com.xht.generate.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.generate.domain.entity.GenTemplateEntity;
import com.xht.generate.domain.request.GenTemplateFormRequest;
import com.xht.generate.domain.response.GenTemplateResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 模板转换器
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GenTemplateConverter extends BasicConverter<GenTemplateEntity, GenTemplateFormRequest, GenTemplateResponse> {




                        }
