package com.xht.generate.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.generate.domain.entity.GenTableColumnEntity;
import com.xht.generate.domain.form.GenColumnInfoForm;
import com.xht.generate.domain.response.GenTableColumnResp;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 字段信息转换器
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GenTableColumnConverter extends BasicConverter<GenTableColumnEntity, GenColumnInfoForm, GenTableColumnResp> {

}
