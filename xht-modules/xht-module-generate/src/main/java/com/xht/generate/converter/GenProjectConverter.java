package com.xht.generate.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.generate.domain.entity.GenProjectEntity;
import com.xht.generate.domain.request.GenProjectFormRequest;
import com.xht.generate.domain.response.GenProjectResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 项目转换器
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GenProjectConverter extends BasicConverter<GenProjectEntity, GenProjectFormRequest, GenProjectResponse> {




                        }
