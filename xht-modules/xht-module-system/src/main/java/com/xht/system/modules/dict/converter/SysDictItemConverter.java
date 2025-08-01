package com.xht.system.modules.dict.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.system.modules.dict.domain.entity.SysDictItemEntity;
import com.xht.system.modules.dict.domain.request.SysDictItemFormRequest;
import com.xht.system.modules.dict.domain.response.SysDictItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 字典项转换器
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysDictItemConverter extends BasicConverter<SysDictItemEntity, SysDictItemFormRequest, SysDictItemResponse> {

}
