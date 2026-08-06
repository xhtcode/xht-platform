package com.xht.platform.type.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.platform.type.domain.form.GenTypeMappingForm;
import com.xht.platform.type.entity.GenTypeMappingEntity;
import com.xht.platform.type.domain.response.GenTypeMappingResponse;
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
