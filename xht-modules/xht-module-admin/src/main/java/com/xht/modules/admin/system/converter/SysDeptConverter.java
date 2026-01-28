package com.xht.modules.admin.system.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.modules.admin.system.domain.form.SysDeptForm;
import com.xht.modules.admin.system.domain.response.SysDeptResponse;
import com.xht.modules.admin.system.entity.SysDeptEntity;
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
public interface SysDeptConverter extends BasicConverter<SysDeptEntity, SysDeptForm, SysDeptResponse> {


    /**
     * 将创建请求对象转换为实体对象。
     *
     * @param form 创建请求对象
     * @return 转换后的实体对象
     */
    @Override
    SysDeptEntity toEntity(SysDeptForm form);

    /**
     * 将SysDeptEntity对象转换为Map集合
     *
     * @param entity 部门实体对象
     * @return 包含部门信息的Map集合
     */
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
        // 添加时间相关信息
        map.put("createTime", entity.getCreateTime());    // 创建时间
        map.put("updateTime", entity.getUpdateTime());    // 更新时间
        // 添加操作人相关信息
        map.put("createBy", entity.getCreateBy());    // 创建人
        map.put("updateBy", entity.getUpdateBy());    // 更新人
        return map;
    }

}