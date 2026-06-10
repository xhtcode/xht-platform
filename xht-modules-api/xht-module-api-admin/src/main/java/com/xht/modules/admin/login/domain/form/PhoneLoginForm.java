package com.xht.modules.admin.login.domain.form;

import com.xht.framework.common.domain.form.BasicForm;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 手机号登录请求参数
 *
 * @author xht
 **/
@Data
@Schema(description = "手机号登录请求参数")
public class PhoneLoginForm extends BasicForm {

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phone;

    /**
     * 手机号验证码
     */
    @Schema(description = "手机号验证码")
    private String phoneCode;

}
