package com.xht.modules.admin.system.domain.form;

import com.xht.framework.core.domain.form.BasicForm;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 用户绑定角色请求参数
 *
 * @author xht
 **/
@Data
public class UserBindRoleForm extends BasicForm {

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 角色ID列表
     */
    @Schema(description = "角色ID列表")
    private List<Long> roleIds;


}
