package com.xht.modules.admin.system.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.modules.admin.system.entity.SysUserDetailEntity;
import com.xht.modules.admin.system.domain.form.SysUserDetailForm;
import com.xht.modules.admin.system.domain.response.SysUserDetailResponse;
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
public interface SysUserDetailConverter extends BasicConverter<SysUserDetailEntity, SysUserDetailForm, SysUserDetailResponse> {
}
