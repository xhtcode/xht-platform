package com.xht.modules.admin.router.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xht.framework.core.domain.response.BasicResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述 ：路由元信息响应对象
 *
 * @author xht
 **/
@Data
@Schema(description = "路由元信息响应对象")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RouterMetaDTO extends BasicResponse {

    /**
     * 设置该路由在侧边栏和面包屑中展示的名字
     */
    @Schema(description = "设置该路由在侧边栏和面包屑中展示的名字")
    private String title;

    /**
     * 设置该路由的图标，对应路径src/assets/icons/svg
     */
    @Schema(description = "设置该路由的图标，对应路径src/assets/icons/svg")
    private String icon;

    /**
     * 当路由设置了该属性，则会高亮相对应的侧边栏。
     */
    @Schema(description = "当路由设置了该属性，则会高亮相对应的侧边栏。")
    private String activeMenuPath;

    /**
     * 是否外链（true）
     */
    @Schema(description = "是否外链")
    private boolean linkStatus;

    /**
     * 是否隐藏（true）
     */
    @Schema(description = "是否隐藏")
    private boolean hiddenStatus;

    /**
     * 是否全屏（true）
     */
    @Schema(description = "是否全屏")
    private boolean fullStatus;

    /**
     * 是否固定在 tabs nav（true）
     */
    @Schema(description = "是否固定在 tabs nav")
    private boolean affixStatus;

    /**
     * 是否缓存 （true）
     */
    @Schema(description = "是否缓存")
    private boolean keepAliveStatus;

    /**
     * 标识后端路由
     */
    @Schema(description = "标识后端路由")
    private boolean backstage = true;

    /**
     * 菜单类型
     */
    @Schema(description = "菜单类型")
    private String menuType;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private int rank;

}
