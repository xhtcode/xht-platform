package com.xht.system.modules.dict.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.system.modules.dict.domain.entity.SysDictEntity;
import com.xht.system.modules.dict.domain.request.SysDictFormRequest;
import com.xht.system.modules.dict.domain.response.SysDictResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 字典类型转换
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysDictConverter extends BasicConverter<SysDictEntity, SysDictFormRequest, SysDictResponse> {

}
