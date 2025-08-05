package com.xht.system.modules.dept.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.system.modules.dept.domain.entity.SysDeptEntity;
import com.xht.system.modules.dept.domain.request.SysDeptFormRequest;
import com.xht.system.modules.dept.domain.response.SysDeptResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统部门转换器
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysDeptConverter extends BasicConverter<SysDeptEntity, SysDeptFormRequest, SysDeptResponse> {


    /**
     * 将创建请求对象转换为实体对象。
     *
     * @param formRequest 创建请求对象
     * @return 转换后的实体对象
     */
    @Override
    SysDeptEntity toEntity(SysDeptFormRequest formRequest);

    default Map<String, Object> toMap(SysDeptEntity entity) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("id", entity.getId());
        map.put("parentId", entity.getParentId());
        map.put("deptName", entity.getDeptName());
        map.put("deptCode", entity.getDeptCode());
        map.put("deptSort", entity.getDeptSort());
        map.put("deptStatus", entity.getDeptStatus());
        map.put("email", entity.getEmail());
        map.put("phone", entity.getPhone());
        map.put("remark", entity.getRemark());
        map.put("createTime", entity.getCreateTime());    // 创建时间
        map.put("updateTime", entity.getUpdateTime());    // 更新时间
        map.put("createBy", entity.getCreateBy());    // 创建人
        map.put("updateBy", entity.getUpdateBy());    // 更新人
        return map;
    }
}