package com.xht.platform.system.domain.form;

import com.xht.framework.common.domain.form.BasicForm;
import com.xht.framework.validation.Groups;
import com.xht.platform.system.enums.RoleStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 角色类型表单请求参数
 *
 * @author xht
 **/
@Data
@Schema(description = "角色类型表单请求参数")
public class SysRoleTypeForm extends BasicForm {

    /**
     * 类型编码
     */
    @NotEmpty(message = "类型编码参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "类型编码")
    private String typeCode;

    /**
     * 类型名称
     */
    @NotEmpty(message = "类型名称参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "类型名称")
    private String typeName;

    /**
     * 状态（0正常 1停用）
     */
    @NotNull(message = "状态参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "状态（0正常 1停用）")
    private RoleStatusEnum typeStatus;

    /**
     * 显示顺序
     */
    @NotNull(message = "显示顺序参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "显示顺序")
    private Integer typeSort;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String remark;

}
