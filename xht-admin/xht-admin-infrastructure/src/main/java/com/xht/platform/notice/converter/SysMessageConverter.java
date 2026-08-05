package com.xht.platform.notice.converter;

import com.xht.framework.mybatis.converter.PageConverter;
import  com.xht.platform.notice.domain.response.SysMessageResponse;
import  com.xht.platform.notice.entity.SysMessageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 描述 ： 系统管理-站内信收件人明细表 Converter
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysMessageConverter extends PageConverter<SysMessageEntity, SysMessageResponse> {

}
