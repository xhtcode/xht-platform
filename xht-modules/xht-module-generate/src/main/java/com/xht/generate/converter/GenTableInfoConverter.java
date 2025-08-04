package com.xht.generate.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.generate.domain.entity.GenTableInfoEntity;
import com.xht.generate.domain.request.GenTableInfoFormRequest;
import com.xht.generate.domain.response.GenTableInfoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 表信息转换器
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GenTableInfoConverter extends BasicConverter<GenTableInfoEntity, GenTableInfoFormRequest, GenTableInfoResponse> {




                        }
