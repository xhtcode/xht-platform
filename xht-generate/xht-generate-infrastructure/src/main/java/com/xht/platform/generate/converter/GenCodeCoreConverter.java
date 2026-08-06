package com.xht.platform.generate.converter;

import com.xht.framework.common.converter.IConverter;
import com.xht.platform.generate.bo.GenCodeCoreBo;
import com.xht.platform.generate.domain.response.GenCodeCoreResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 描述： 代码生成核心转换类
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GenCodeCoreConverter extends IConverter<GenCodeCoreBo, GenCodeCoreResponse> {

}
