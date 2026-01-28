package com.xht.modules.generate.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.modules.generate.domain.form.GenTemplateForm;
import com.xht.modules.generate.domain.response.GenTemplateResponse;
import com.xht.modules.generate.entity.GenTemplateEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 模板转换器
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GenTemplateConverter extends BasicConverter<GenTemplateEntity, GenTemplateForm, GenTemplateResponse> {

}
