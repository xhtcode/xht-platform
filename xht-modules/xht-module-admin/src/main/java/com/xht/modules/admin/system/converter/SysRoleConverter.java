package com.xht.modules.admin.system.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.modules.admin.system.entity.SysRoleEntity;
import com.xht.api.system.domain.form.SysRoleForm;
import com.xht.api.system.domain.response.SysRoleResponse;
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
