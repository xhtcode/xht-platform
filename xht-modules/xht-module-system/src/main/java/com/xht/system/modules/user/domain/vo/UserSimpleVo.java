package com.xht.system.modules.user.domain.vo;

import com.xht.framework.core.domain.vo.IVO;
import com.xht.system.modules.user.common.enums.UserStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 用户响应信息
 * @author xht
 **/
@Data
@Schema(description = "用户响应信息")
@AllArgsConstructor
public class UserSimpleVo implements IVO {
    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String userName;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String nickName;

    /**
     * 头像地址
     */
    @Schema(description = "头像地址")
    private String avatarUrl;

    /**
     * 账号状态(1-正常,2-锁定,3-禁用,4-过期)
     */
    @Schema(description = "账号状态(1-正常,2-锁定,3-禁用,4-过期)")
    private UserStatusEnums userStatus;
}
