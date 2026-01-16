package com.xht.modules.admin.system.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.modules.admin.system.entity.SysPostEntity;
import com.xht.modules.admin.system.domain.form.SysPostForm;
import com.xht.modules.admin.system.domain.response.SysPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 部门岗位转换器
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysPostConverter extends BasicConverter<SysPostEntity, SysPostForm, SysPostResponse> {

    /**
     * 将创建请求对象转换为实体对象。
     *
     * @param form 创建请求对象
     * @return 转换后的实体对象
     */
    @Override
    SysPostEntity toEntity(SysPostForm form);

}
