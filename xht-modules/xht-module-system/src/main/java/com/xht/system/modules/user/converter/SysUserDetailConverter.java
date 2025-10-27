package com.xht.system.modules.user.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.system.modules.user.domain.entity.SysUserDetailEntity;
import com.xht.system.modules.user.domain.form.SysUserDetailBasicForm;
import com.xht.system.modules.user.domain.response.SysUserDetailResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 用户信息转换器
 *
 * @author xht
 **/
@SuppressWarnings("unused")
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysUserDetailConverter extends BasicConverter<SysUserDetailEntity, SysUserDetailBasicForm, SysUserDetailResponse> {
}
