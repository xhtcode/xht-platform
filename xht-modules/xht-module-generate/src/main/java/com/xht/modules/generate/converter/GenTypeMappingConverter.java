package com.xht.modules.generate.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.modules.generate.entity.GenTypeMappingEntity;
import com.xht.modules.generate.domain.form.GenTypeMappingForm;
import com.xht.modules.generate.domain.response.GenTypeMappingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 字段映射转换器
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GenTypeMappingConverter extends BasicConverter<GenTypeMappingEntity, GenTypeMappingForm, GenTypeMappingResponse> {

}
