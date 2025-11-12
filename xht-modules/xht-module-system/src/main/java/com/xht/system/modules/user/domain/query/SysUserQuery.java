package com.xht.system.modules.user.domain.query;

import com.xht.framework.core.domain.query.PageBasicQuery;
import com.xht.framework.core.enums.UserStatusEnums;
import com.xht.framework.core.enums.UserTypeEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用户查询分页查询参数
 *
 * @author xht
 **/
@Data
@Schema(description = "用于分页查询用户信息的分页查询参数")
public class SysUserQuery extends PageBasicQuery {

    /**
     * 用户类型
     */
    @Schema(description = "用户类型")
    private UserTypeEnums userType;

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
    private UserStatusEnums userStatus;

    /**
     * 手机号码
     */
    @Schema(description = "手机号码")
    private String userPhone;

    /**
     * 部门id
     */
    @Schema(description = "部门ID")
    private Long deptId;


}
