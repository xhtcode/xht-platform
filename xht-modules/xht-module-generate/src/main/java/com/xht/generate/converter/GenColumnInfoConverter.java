package com.xht.generate.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.generate.domain.entity.GenColumnInfoEntity;
import com.xht.generate.domain.request.GenColumnInfoFormRequest;
import com.xht.generate.domain.response.GenColumnInfoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 字段信息转换器
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GenColumnInfoConverter extends BasicConverter<GenColumnInfoEntity, GenColumnInfoFormRequest, GenColumnInfoResponse> {




                        }
