package com.xht.platform.system.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xht.framework.common.domain.response.MetaResponse;
import com.xht.platform.system.enums.RoleStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 角色类型响应信息
 *
 * @author xht
 **/
@Data
@Schema(description = "角色类型响应信息")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysRoleTypeResponse extends MetaResponse {

    /**
     * 角色类型ID
     */
    @Schema(description = "角色类型ID")
    private Long id;

    /**
     * 类型编码
     */
    @Schema(description = "类型编码")
    private String typeCode;

    /**
     * 类型名称
     */
    @Schema(description = "类型名称")
    private String typeName;

    /**
     * 状态（0正常 1停用）
     */
    @Schema(description = "状态（0正常 1停用）")
    private RoleStatusEnum typeStatus;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer typeSort;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String remark;

}
