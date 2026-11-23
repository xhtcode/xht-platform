package com.xht.platform.system.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.platform.system.domain.form.SysRoleTypeForm;
import com.xht.platform.system.domain.response.SysRoleTypeResponse;
import com.xht.platform.system.entity.SysRoleTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 角色类型转换器
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysRoleTypeConverter extends BasicConverter<SysRoleTypeEntity, SysRoleTypeForm, SysRoleTypeResponse> {

}
