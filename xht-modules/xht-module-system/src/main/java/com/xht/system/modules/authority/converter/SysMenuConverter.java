package com.xht.system.modules.authority.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.system.modules.authority.domain.entity.SysMenuEntity;
import com.xht.system.modules.authority.domain.request.SysMenuForm;
import com.xht.system.modules.authority.domain.response.SysMenuResp;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 菜单转换器
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysMenuConverter extends BasicConverter<SysMenuEntity, SysMenuForm, SysMenuResp> {

    /**
     * 转换为Map
     *
     * @param entity 实体
     * @param isSelect 是否为选择菜单
     * @return Map
     */
    default Map<String, Object> toMap(SysMenuEntity entity, boolean isSelect) {
        if (entity == null) {
            return Collections.emptyMap();
        }
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("menuType", entity.getMenuType());    // 菜单类型
        map.put("menuName", entity.getMenuName());    // 菜单名称
        map.put("menuIcon", entity.getMenuIcon());    // 菜单图标
        map.put("menuSort", entity.getMenuSort());    // 菜单排序
        if (!isSelect) {
            map.put("expanded", false);
        }
        if (isSelect) {
            map.put("menuPath", entity.getMenuPath());    // 路由地址
            map.put("menuHidden", entity.getMenuHidden());    // 显示状态 (0显示 1隐藏)
            map.put("menuCache", entity.getMenuCache());    // 是否缓存 （0是 1否）
            map.put("menuStatus", entity.getMenuStatus());    // 菜单状态 （0正常 1停用）
            map.put("menuAuthority", entity.getMenuAuthority());    // 菜单权限字符串
            map.put("viewName", entity.getViewName());    // 组件视图名称
            map.put("viewPath", entity.getViewPath());    // 组件视图路径
            map.put("frameFlag", entity.getFrameFlag());    // 是否为外链
            map.put("createTime", entity.getCreateTime());    // 创建时间
            map.put("updateTime", entity.getUpdateTime());    // 更新时间
            map.put("createBy", entity.getCreateBy());    // 创建人
            map.put("updateBy", entity.getUpdateBy());    // 更新人
        }
        return map;
    }


}
