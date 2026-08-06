package com.xht.platform.template.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.platform.template.domain.form.GenTemplateGroupForm;
import com.xht.platform.template.entity.GenTemplateGroupEntity;
import com.xht.platform.template.domain.response.GenTemplateGroupResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 项目转换器
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GenTemplateGroupConverter extends BasicConverter<GenTemplateGroupEntity, GenTemplateGroupForm, GenTemplateGroupResponse> {

}
