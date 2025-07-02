package com.xht.system.dept.domain.request;

import com.xht.framework.core.domain.request.PageQueryRequest;
import com.xht.framework.core.enums.SystemFlagEnums;
import com.xht.system.dept.common.enums.DeptPostStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 查询部门岗位分页查询参数
 *
 * @author xht
 **/
@Data
@Schema(description = "查询部门岗位分页查询参数")
public class SysDeptPostQueryRequest extends PageQueryRequest {

    /**
     * 部门id
     */
    @Schema(description = "部门id")
    private Long deptId;

    /**
     * 岗位编码
     */
    @Schema(description = "岗位编码")
    private String postCode;

    /**
     * 岗位名称
     */
    @Schema(description = "岗位名称")
    private String postName;

    /**
     * 岗位状态（0正常 1停用）
     */
    @Schema(description = "岗位状态")
    private DeptPostStatusEnums postStatus;

    /**
     * 系统内置
     */
    @Schema(description = "系统内置")
    private SystemFlagEnums systemFlag;
}
