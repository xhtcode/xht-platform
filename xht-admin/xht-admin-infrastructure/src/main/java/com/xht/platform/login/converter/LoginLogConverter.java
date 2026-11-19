package com.xht.platform.login.converter;

import com.xht.framework.mybatis.converter.PageConverter;
import com.xht.platform.login.domain.response.UserLoginLogResponse;
import com.xht.platform.login.entity.SysLoginLogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 描述： 登录日志转换类
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LoginLogConverter extends PageConverter<SysLoginLogEntity, UserLoginLogResponse> {

}
