package com.xht.system.modules.log.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.system.modules.log.domian.entity.SysLogEntity;
import com.xht.system.modules.log.domian.request.SysLogFormRequest;
import com.xht.system.modules.log.domian.response.SysLogResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 系统日志管理
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysLogConverter extends BasicConverter<SysLogEntity, SysLogFormRequest, SysLogResponse> {
}
