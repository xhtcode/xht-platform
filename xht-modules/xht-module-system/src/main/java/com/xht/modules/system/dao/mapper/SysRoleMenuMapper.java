package com.xht.modules.system.dao.mapper;

import com.xht.framework.mybatis.mapper.BaseMapperX;
import com.xht.modules.common.enums.MenuStatusEnums;
import com.xht.modules.system.domain.entity.SysRoleMenuEntity;
import com.xht.modules.system.domain.response.SysMenuResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

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

    /**
     * 用户id获取菜单集合
     *
     * @param userId 用户id
     * @return 菜单集合
     */
    Set<String> findPermissionCodeByUserId(@Param("userId") Long userId);

    /**
     * 根据用户ID获取路由菜单集合
     *
     * @param userId 用户ID
     * @return 路由菜单集合
     */
    List<SysMenuResponse> findRouterByUserId(@Param("userId") Long userId);
}




