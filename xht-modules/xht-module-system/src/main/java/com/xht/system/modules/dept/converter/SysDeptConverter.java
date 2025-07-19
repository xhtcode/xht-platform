package com.xht.system.modules.dept.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.system.modules.dept.domain.entity.SysDeptEntity;
import com.xht.system.modules.dept.domain.request.SysDeptFormRequest;
import com.xht.system.modules.dept.domain.response.SysDeptResponse;
import com.xht.system.modules.dept.domain.vo.SysDeptTreeVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.util.CollectionUtils;

import java.util.*;

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

    /**
     * 将实体对象转换为响应对象
     *
     * @param entity 实体对象，包含从数据库或其他数据源获取的数据
     * @return 转换后的响应对象，用于返回给客户端
     */
    SysDeptTreeVo toVo(SysDeptEntity entity);

    /**
     * 将实体对象列表转换为响应对象列表
     *
     * @param entityList 实体对象列表，包含多个从数据库或其他数据源获取的实体对象
     * @return 转换后的响应对象列表，用于返回给客户端
     */
    default List<SysDeptTreeVo> toVo(List<SysDeptEntity> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            return Collections.emptyList();
        }
        List<SysDeptTreeVo> list = new ArrayList<>(entityList.size());
        for (SysDeptEntity sysDictEntity : entityList) {
            list.add(toVo(sysDictEntity));
        }
        return list;
    }

    default Map<String, Object> toMap(SysDeptEntity entity) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("id", entity.getId());
        map.put("parentId", entity.getParentId());
        map.put("deptName", entity.getDeptName());
        map.put("deptCode", entity.getDeptCode());
        map.put("deptSort", entity.getDeptSort());
        map.put("deptStatus", entity.getDeptStatus());
        map.put("leaderName", entity.getLeaderName());
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