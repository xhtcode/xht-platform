package com.xht.modules.generate.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.modules.generate.domain.form.GenTemplateGroupForm;
import com.xht.modules.generate.domain.response.GenTemplateGroupResponse;
import com.xht.modules.generate.entity.GenTemplateGroupEntity;
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
