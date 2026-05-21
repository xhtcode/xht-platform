package com.xht.auth.authentication.converter;

import com.xht.auth.authentication.entity.SysLoginLogEntity;
import com.xht.framework.core.converter.IConverter;
import com.xht.framework.log.event.LoginLogApplicationEvent;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 登录日志转换器
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysLoginLogConverter extends IConverter<LoginLogApplicationEvent, SysLoginLogEntity> {
}
