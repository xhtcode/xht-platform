package com.xht.modules.admin.login.domain.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xht.framework.common.domain.form.BasicForm;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 表单登录请求参数
 *
 * @author xht
 **/
@Data
@Schema(description = "表单登录请求参数")
public class PasswordLoginForm extends BasicForm {

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    @JsonProperty("username")
    private String username;

    /**
     * 密码
     */
    @Schema(description = "密码")
    @JsonProperty("password")
    private String password;

    /**
     * 验证码
     */
    @Schema(description = "验证码值")
    @JsonProperty("captcha_code")
    private String captchaCode;

    /**
     * 验证码key，用于验证验证码的有效性
     */
    @Schema(description = "验证码key")
    @JsonProperty("captcha_key")
    private String captchaKey;

}
