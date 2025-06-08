package com.xht.system.authority.mapper;

import com.xht.framework.mybatis.mapper.BaseMapperX;
import com.xht.system.authority.common.enums.MenuStatusEnums;
import com.xht.system.authority.domain.entity.SysRoleMenuEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色菜单关系表 数据层接口
 *
 * @author xht
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapperX<SysRoleMenuEntity> {

    /**
     * 根据角色ID查询菜单ID集合
     *
     * @param menuStatus 菜单状态
     * @param roleId     角色ID
     * @return 菜单ID集合
     */
    List<Long> selectMenuIdByRoleId(MenuStatusEnums menuStatus, String roleId);
}




