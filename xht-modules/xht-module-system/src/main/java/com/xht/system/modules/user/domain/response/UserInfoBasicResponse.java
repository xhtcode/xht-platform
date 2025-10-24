package com.xht.system.modules.user.domain.response;

import com.xht.framework.core.domain.response.IResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用户基本信息响应信息
 *
 * @author xht
 **/
@Data
@Schema(description = "用户基本信息响应信息")
public sealed class UserInfoBasicResponse implements IResponse permits SysUserAdminResponse {

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long userId;

}
