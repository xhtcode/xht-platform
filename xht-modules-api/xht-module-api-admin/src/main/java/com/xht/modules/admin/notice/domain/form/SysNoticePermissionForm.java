package com.xht.modules.admin.notice.domain.form;

import com.xht.framework.core.domain.form.BasicForm;
import com.xht.framework.core.validation.Groups;
import com.xht.modules.admin.notice.enums.NoticePermTypeEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 系统管理-通知权限
 *
 * @author xht
 */
@Data
@Schema(description = "系统管理-通知权限")
public class SysNoticePermissionForm extends BasicForm {

    /**
     * 权限类型(0:角色权限;1:用户权限;2:部门权限)
     */
    @Schema(description = "权限类型")
    @NotNull(message = "权限类型参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    private NoticePermTypeEnums permType;

    /**
     * 权限值（单个角色ID/用户ID/部门ID，不再存多个）
     */
    @Schema(description = "权限值")
    @NotBlank(message = "权限值参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    private String permValue;

}