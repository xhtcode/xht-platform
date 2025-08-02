package com.xht.generate.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.generate.domain.entity.GenTypeMappingEntity;
import com.xht.generate.domain.request.GenTypeMappingFormRequest;
import com.xht.generate.domain.response.GenTypeMappingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 字段映射转换器
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GenTypeMappingConverter extends BasicConverter<GenTypeMappingEntity, GenTypeMappingFormRequest, GenTypeMappingResponse> {




                        }
