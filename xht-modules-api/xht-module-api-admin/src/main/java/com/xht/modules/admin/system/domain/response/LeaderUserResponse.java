package com.xht.modules.admin.system.domain.response;

import com.xht.framework.core.domain.response.BasicResponse;
import com.xht.framework.core.enums.UserStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 岗位用户响应信息
 * @author xht
 **/
@Data
@Schema(description = "岗位用户响应信息")
public class LeaderUserResponse extends BasicResponse {

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

}
