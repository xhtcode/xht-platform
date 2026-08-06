package com.xht.platform.template.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.platform.template.domain.form.GenTemplateForm;
import com.xht.platform.template.entity.GenTemplateEntity;
import com.xht.platform.template.domain.response.GenTemplateResponse;
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
