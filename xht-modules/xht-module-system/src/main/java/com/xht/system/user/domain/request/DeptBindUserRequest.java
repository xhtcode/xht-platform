package com.xht.system.user.domain.request;

import com.xht.framework.core.domain.request.FormRequest;
import com.xht.framework.core.domain.request.IRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 部门绑定用户请求参数
 *
 * @author xht
 **/
@Data
public class DeptBindUserRequest extends FormRequest {

    /**
     * 部门ID
     */
    @NotNull(message = "部门ID不能为空")
    @Schema(description = "部门ID")
    private Long deptId;

    /**
     * 用户ID列表
     */
    @NotNull(message = "用户ID列表不能为空")
    @Schema(description = "用户ID列表")
    private List<Long> userIds;


}
