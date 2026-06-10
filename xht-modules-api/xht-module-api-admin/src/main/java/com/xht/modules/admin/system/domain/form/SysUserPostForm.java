package com.xht.modules.admin.system.domain.form;

import com.xht.framework.common.domain.form.BasicForm;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 修改岗位类型请求参数
 *
 * @author xht
 **/
@Data
@Schema(description = "修改岗位类型请求参数")
public class SysUserPostForm extends BasicForm {


    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空")
    @Schema(description = "user_id")
    private Long userId;

    /**
     * 部门id
     */
    @NotNull(message = "部门id不能为空")
    @Schema(description = "dept_id")
    private Long deptId;

    /**
     * 岗位id
     */
    @NotNull(message = "岗位id不能为空")
    @Schema(description = "post_id")
    private Long postId;


}
