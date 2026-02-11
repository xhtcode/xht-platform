package com.xht.modules.admin.oauth2.domain.form;

import com.xht.framework.core.domain.form.BasicForm;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * OAuth2客户端密码表单请求参数
 *
 * @author xht
 **/
@Data
@Schema(description = "OAuth2客户端密码表单请求参数")
public class SysOauth2ClientPwdForm extends BasicForm {

    /**
     * 客户端标识
     */
    @NotBlank(message = "客户端标识参数不合法")
    @Schema(description = "客户端标识")
    private String clientId;

    /**
     * 客户端密钥
     */
    @NotBlank(message = "客户端密钥参数不合法")
    @Schema(description = "客户端密钥")
    private String clientSecret;
}
