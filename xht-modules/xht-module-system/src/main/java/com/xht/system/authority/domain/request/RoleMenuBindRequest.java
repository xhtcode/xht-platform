package com.xht.system.authority.domain.request;

import com.xht.framework.core.domain.request.FormRequest;
import com.xht.framework.core.domain.request.IRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.util.List;

/**
 * @author xht
 **/
@Data
@Schema(description = "角色菜单绑定请求参数")
public class RoleMenuBindRequest extends FormRequest {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(name = "角色ID")
    @NotNull(message = "角色ID不能为空")
    private Long roleId;

    @Schema(name = "菜单ID")
    private List<Long> menuIds;
}
