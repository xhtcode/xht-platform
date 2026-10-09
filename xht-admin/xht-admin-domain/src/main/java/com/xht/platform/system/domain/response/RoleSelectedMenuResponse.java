package com.xht.platform.system.domain.response;

import com.xht.framework.common.domain.response.BasicResponse;
import com.xht.framework.utils.tree.INode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 角色已选菜单响应参数
 *
 * @author xht
 **/
@Data
@Schema(description = "角色已选菜单响应参数")
public class RoleSelectedMenuResponse extends BasicResponse {

    /**
     * 已选的菜单id
     */
    @Schema(description = "已选的菜单id")
    private List<Long> checkedMenuIds;

    /**
     * 菜单总数
     */
    @Schema(description = "菜单总数")
    private Integer menuTotal;

    /**
     * 菜单列表
     */
    @Schema(description = "菜单列表")
    private List<INode<Long>> menuList;

}