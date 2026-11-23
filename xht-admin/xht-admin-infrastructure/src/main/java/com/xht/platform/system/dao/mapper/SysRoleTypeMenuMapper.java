package com.xht.platform.system.dao.mapper;

import com.xht.framework.mybatis.mapper.BaseMapperX;
import com.xht.platform.system.entity.SysRoleTypeMenuEntity;
import com.xht.platform.system.enums.MenuStatusEnum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色类型菜单关系表 数据层接口
 *
 * @author xht
 */
@Mapper
public interface SysRoleTypeMenuMapper extends BaseMapperX<SysRoleTypeMenuEntity> {

    /**
     * 根据角色类型ID查询菜单ID集合
     *
     * @param menuStatus 菜单状态
     * @param roleTypeId 角色类型ID
     * @return 菜单ID集合
     */
    List<Long> selectMenuIdByTypeId(MenuStatusEnum menuStatus, @Param("roleTypeId") Long roleTypeId);

}
