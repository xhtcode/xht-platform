package com.xht.modules.admin.area.converter;

import com.xht.framework.mybatis.converter.PageConverter;
import com.xht.modules.admin.area.domain.form.SysAreaForm;
import com.xht.modules.admin.area.domain.response.SysAreaResponse;
import com.xht.modules.admin.area.entity.SysAreaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 系统管理 - 行政区划
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysAreaConverter extends PageConverter<SysAreaEntity, SysAreaResponse> {

    /**
     * 将创建请求对象转换为实体对象
     *
     * @param areaForm 创建请求对象，非null
     * @return 转换后的实体对象，非null
     */
    SysAreaEntity toEntity(SysAreaForm areaForm);

}
