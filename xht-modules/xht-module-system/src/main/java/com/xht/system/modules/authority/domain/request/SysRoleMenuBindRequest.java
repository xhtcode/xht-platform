package com.xht.system.modules.authority.domain.request;

import com.xht.framework.core.domain.request.FormRequest;
import com.xht.framework.web.validation.Groups;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.io.Serial;
import java.util.List;

/**
 * @author xht
 **/
@Data
@Schema(description = "角色菜单绑定请求参数")
public class SysRoleMenuBindRequest extends FormRequest {

    @Serial
    private static final long serialVersionUID = 1L;

    @Null(message = "角色ID唯一标识必须为空", groups = {Groups.Create.class})
    @NotNull(message = "角色ID唯一标识参数不合法", groups = {Groups.Update.class})
    @Positive(message = "角色ID唯一标识参数不合法", groups = {Groups.Update.class})
    @Schema(name = "角色ID")
    private Long roleId;

    @NotEmpty(message = "菜单ID参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(name = "菜单ID")
    private List<Long> menuIds;

}
