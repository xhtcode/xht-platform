package com.xht.modules.admin.system.converter;

import com.xht.framework.core.utils.tree.INode;
import com.xht.framework.core.utils.tree.TreeNode;
import com.xht.framework.core.utils.tree.TreeUtils;
import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.modules.admin.system.entity.SysMenuEntity;
import com.xht.api.system.domain.form.SysMenuForm;
import com.xht.api.system.domain.response.SysMenuResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.*;

/**
 * 菜单转换器
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysMenuConverter extends BasicConverter<SysMenuEntity, SysMenuForm, SysMenuResponse> {

    /**
     * 转换为树形结构
     *
     * @param menuList 菜单列表
     * @return 树形结构
     */
    default List<INode<Long>> toTree(List<SysMenuEntity> menuList) {
        List<INode<Long>> treeNodeList = new ArrayList<>();
        for (SysMenuEntity entity : menuList) {
            TreeNode<Long> node = new TreeNode<>(entity.getId(), entity.getParentId(), entity.getMenuSort());
            node.setExtra(toMap(entity));
            treeNodeList.add(node);
        }
        return TreeUtils.buildList(treeNodeList, false);
    }

    /**
     * 转换为Map
     *
     * @param entity 实体
     * @return Map
     */
    private Map<String, Object> toMap(SysMenuEntity entity) {
        if (entity == null) {
            return Collections.emptyMap();
        }
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("menuType", entity.getMenuType());    // 菜单类型
        map.put("menuName", entity.getMenuName());    // 菜单名称
        map.put("menuIcon", entity.getMenuIcon());    // 菜单图标
        map.put("menuSort", entity.getMenuSort());    // 菜单排序
        map.put("frameFlag", entity.getFrameFlag());    // 是否为外链
        map.put("menuStatus", entity.getMenuStatus());    // 菜单状态 （0正常 1停用）
        map.put("menuAuthority", entity.getMenuAuthority());    // 菜单权限字符串
        map.put("createTime", entity.getCreateTime());    // 创建时间
        map.put("updateTime", entity.getUpdateTime());    // 更新时间
        map.put("createBy", entity.getCreateBy());    // 创建人
        map.put("updateBy", entity.getUpdateBy());    // 更新人
        return map;
    }


}
