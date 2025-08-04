package com.xht.system.modules.authority.domain.request;

import com.xht.framework.core.domain.request.FormRequest;
import com.xht.system.modules.user.common.enums.PositionNatureEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 用户绑定部门岗位请求参数
 *
 * @author xht
 **/
@Data
@Schema(description = "用户绑定部门岗位请求参数")
public class UserBindDeptPostRequest extends FormRequest {

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 部门ID
     */
    @NotNull(message = "部门ID不合法")
    @Schema(description = "部门ID")
    private Long deptId;

    /**
     * 岗位ID
     */
    @Schema(description = "岗位ID")
    private Long postId;

    /**
     * 岗位性质
     */
    @Schema(description = "岗位性质")
    private PositionNatureEnums positionNature;

}
