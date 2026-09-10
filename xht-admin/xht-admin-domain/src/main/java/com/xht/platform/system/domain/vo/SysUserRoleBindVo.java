package com.xht.platform.system.domain.vo;

import com.xht.framework.common.domain.vo.XhtVO;
import com.xht.framework.common.enums.UserStatusEnum;
import com.xht.framework.common.enums.UserTypeEnum;
import com.xht.platform.system.domain.response.SysRoleResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 描述： 用户角色绑定信息VO
 *
 * @author xht
 **/
@Data
@Schema(description = "用户角色绑定信息VO")
public class SysUserRoleBindVo implements XhtVO {

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 用户类型
     */
    @Schema(description = "用户类型")
    private UserTypeEnum userType;

    /**
     * 用户账号
     */
    @Schema(description = "用户账号")
    private String userName;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String nickName;

    /**
     * 账号状态
     */
    @Schema(description = "账号状态")
    private UserStatusEnum userStatus;

    /**
     * 绑定角色ID列表
     */
    @Schema(description = "已绑定角色ID列表")
    private List<Long> bindRoleIds;

    /**
     * 角色列表
     */
    @Schema(description = "角色列表")
    private List<SysRoleResponse> roles;

}
