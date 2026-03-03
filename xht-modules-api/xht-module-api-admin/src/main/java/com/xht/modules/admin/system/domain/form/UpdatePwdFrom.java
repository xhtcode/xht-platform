package com.xht.modules.admin.system.domain.form;

import com.xht.framework.core.domain.form.BasicForm;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author xht
 **/
@Data
@Schema(description = "修改密码请求参数")
public class UpdatePwdFrom extends BasicForm {

    /**
     * 旧密码
     */
    @Schema(description = "旧密码")
    @NotNull(message = "旧密码不能为空")
    private String oldPassword;

    /**
     * 新密码
     */
    @Schema(description = "新密码")
    @NotNull(message = "新密码不能为空")
    private String newPassword;

    /**
     * 确认密码
     */
    @Schema(description = "确认密码")
    @NotNull(message = "确认密码不能为空")
    private String confirmPassword;

}
