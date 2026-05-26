package com.xht.modules.admin.login.domain.response;

import com.xht.framework.core.domain.response.BasicResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 登录响应参数
 *
 * @author xht
 **/
@Data
@Schema(description = "登录响应参数")
public class LoginResponse extends BasicResponse {

    /**
     * 访问令牌
     */
    @Schema(description = "访问令牌")
    private String accessToken;

    /**
     * 刷新令牌
     */
    @Schema(description = "刷新令牌")
    private String refreshToken;

    /**
     * 令牌有效期
     */
    @Schema(description = "令牌有效期")
    private Long expiresIn;

}
