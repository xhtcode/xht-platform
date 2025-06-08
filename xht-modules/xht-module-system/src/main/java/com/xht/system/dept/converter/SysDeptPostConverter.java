package com.xht.system.dept.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.system.dept.domain.entity.SysDeptPostEntity;
import com.xht.system.dept.domain.request.SysDeptPostFormRequest;
import com.xht.system.dept.domain.response.SysDeptPostResponse;
import com.xht.system.dept.domain.vo.SysDeptPostSimpleVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * 部门岗位转换器
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysDeptPostConverter extends BasicConverter<SysDeptPostEntity, SysDeptPostFormRequest, SysDeptPostResponse> {

    /**
     * 将创建请求对象转换为实体对象。
     *
     * @param formRequest 创建请求对象
     * @return 转换后的实体对象
     */
    @Override
    SysDeptPostEntity toEntity(SysDeptPostFormRequest formRequest);

    /**
     * 将实体对象转换为响应对象。
     * @param sysDeptPostEntities 实体对象列表
     * @return 转换后的响应对象列表
     */
    List<SysDeptPostSimpleVo> toSimpleVo(List<SysDeptPostEntity> sysDeptPostEntities);
}
