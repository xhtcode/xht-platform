package com.xht.modules.admin.dict.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.modules.admin.dict.entity.SysDictEntity;
import com.xht.api.system.domain.form.SysDictForm;
import com.xht.api.system.domain.response.SysDictResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 字典类型转换
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysDictConverter extends BasicConverter<SysDictEntity, SysDictForm, SysDictResponse> {

}
