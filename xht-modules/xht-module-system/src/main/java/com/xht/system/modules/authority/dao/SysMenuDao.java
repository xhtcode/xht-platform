package com.xht.system.modules.authority.dao;

import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.system.modules.authority.common.enums.MenuStatusEnums;
import com.xht.system.modules.authority.common.enums.MenuTypeEnums;
import com.xht.system.modules.authority.domain.entity.SysMenuEntity;
import com.xht.system.modules.authority.domain.request.SysMenuFormRequest;
import com.xht.system.modules.authority.domain.request.SysMenuQueryRequest;

import java.util.List;

/**
 * 系统菜单管理 Dao
 *
 * @author xht
 **/
public interface SysMenuDao extends MapperRepository<SysMenuEntity> {

    /**
     * 更新菜单信息
     *
     * @param formRequest 菜单信息
     * @return 是否成功
     */
    Boolean updateFormRequest(SysMenuFormRequest formRequest);

    /**
     * 更新菜单状态
     *
     * @param id     菜单ID
     * @param status 菜单状态
     * @return 是否成功
     */
    Boolean updateStatus(Long id, MenuStatusEnums status);

    /**
     * 判断菜单ID是否存在
     *
     * @param menuIds 菜单ID列表
     * @return 是否存在
     */
    Boolean existsMenuIds(List<Long> menuIds);

    /**
     * 获取排除特定菜单类型的菜单列表
     *
     * @param excludedType 需要排除的菜单类型
     * @return 菜单列表
     */
    List<SysMenuEntity> getMenusExcludingType(MenuTypeEnums excludedType);

    /**
     * 查询菜单类型
     *
     * @param menuId 菜单ID
     * @return 只返回菜单类型
     */
    MenuTypeEnums getMenuType(Long menuId);

    /**
     * 根据查询条件获取菜单列表
     *
     * @param queryRequest 查询请求参数
     * @return LambdaQueryWrapper<SysMenuEntity>
     */
    List<SysMenuEntity> getMenuList(SysMenuQueryRequest queryRequest);

    /**
     * 查询可用菜单并构建树形结构
     *
     * @param menuType 菜单类型过滤条件
     * @return 菜单树结构
     */
    List<SysMenuEntity> listMenuTree(MenuTypeEnums menuType);

}
