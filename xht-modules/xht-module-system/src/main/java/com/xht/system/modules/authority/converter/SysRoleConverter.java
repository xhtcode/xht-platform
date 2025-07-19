package com.xht.system.modules.authority.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.system.modules.authority.domain.entity.SysRoleEntity;
import com.xht.system.modules.authority.domain.request.SysRoleFormRequest;
import com.xht.system.modules.authority.domain.response.SysRoleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 系统角色转换器
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysRoleConverter extends BasicConverter<SysRoleEntity, SysRoleFormRequest, SysRoleResponse> {
}
