package com.xht.system.modules.authority.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xht.framework.core.domain.response.IResponse;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述 ：
 *
 * @author xht
 * @version : 1.0
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MetaVo implements IResponse {

    /**
     * 设置该路由在侧边栏和面包屑中展示的名字
     */
    private String title;

    /**
     * 设置该路由的图标，对应路径src/assets/icons/svg
     */
    private String icon;

    /**
     * 当路由设置了该属性，则会高亮相对应的侧边栏。
     */
    private String activeMenuPath;

    /**
     * 是否外链（true）
     */
    private boolean linkStatus;

    /**
     * 是否隐藏（true）
     */
    private boolean hiddenStatus;

    /**
     * 是否全屏（true）
     */
    private boolean fullStatus;

    /**
     * 是否固定在 tabs nav（true）
     */
    private boolean affixStatus;

    /**
     * 是否缓存 （true）
     */
    private boolean keepAliveStatus;

    /**
     * 标识后端路由
     */
    private boolean backstage = true;

    /**
     * 菜单类型
     */
    private String menuType;

    /**
     * 排序
     */
    private int rank;

    /**
     * 角色
     */
    private List<String> roles = new ArrayList<>();
}
