package com.xht.platform.generate.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.platform.form.GenTemplateForm;
import com.xht.platform.generate.entity.GenTemplateEntity;
import com.xht.platform.response.GenTemplateResponse;
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
