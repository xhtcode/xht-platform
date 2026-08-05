package com.xht.platform.system.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import  com.xht.platform.system.domain.form.SysRoleForm;
import  com.xht.platform.system.domain.response.SysRoleResponse;
import  com.xht.platform.system.entity.SysRoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 系统角色转换器
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysRoleConverter extends BasicConverter<SysRoleEntity, SysRoleForm, SysRoleResponse> {

}
