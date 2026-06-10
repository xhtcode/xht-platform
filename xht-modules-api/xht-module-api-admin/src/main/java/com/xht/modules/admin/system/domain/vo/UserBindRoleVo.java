package com.xht.modules.admin.system.domain.vo;

import com.xht.framework.common.domain.vo.IVO;
import com.xht.modules.admin.system.domain.response.SysRoleResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 用户绑定角色VO
 *
 * @author xht
 **/
@Data
@Schema(description = "用户绑定角色VO")
public class UserBindRoleVo implements IVO {

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 已绑定的roleIds
     */
    @Schema(description = "已绑定的roleIds")
    private List<Long> bindRoleIds;

    /**
     * 全部的角色信息
     */
    @Schema(description = "全部的角色信息")
    private List<SysRoleResponse> roles;

    /**
     * 是否全选
     */
    @Schema(description = "是否全选")
    private boolean allChecked;

    /**
     * 是否半选
     */
    @Schema(description = "是否半选")
    private boolean indeterminate;

}
