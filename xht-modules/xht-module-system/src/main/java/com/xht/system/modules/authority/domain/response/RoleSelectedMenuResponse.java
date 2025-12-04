package com.xht.system.modules.authority.domain.response;

import com.xht.framework.core.domain.response.BasicResponse;
import com.xht.framework.core.utils.tree.INode;
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
     * 是否全选
     */
    @Schema(description = "是否全选")
    private Boolean checkAll;

    /**
     * 已选的菜单id
     */
    @Schema(description = "已选的菜单id")
    private List<Long> checkedKeys;

    /**
     * 菜单列表
     */
    @Schema(description = "菜单列表")
    private List<INode<Long>> menuList;

}
