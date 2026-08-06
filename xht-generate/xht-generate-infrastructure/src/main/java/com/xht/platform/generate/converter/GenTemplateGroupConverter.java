package com.xht.platform.generate.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.platform.form.GenTemplateGroupForm;
import com.xht.platform.generate.entity.GenTemplateGroupEntity;
import com.xht.platform.response.GenTemplateGroupResponse;
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
