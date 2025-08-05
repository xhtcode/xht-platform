package com.xht.system.modules.dept.domain.request;

import com.xht.framework.core.domain.request.QueryRequest;
import com.xht.system.modules.dept.common.enums.DeptStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 部门树查询参数
 *
 * @author xht
 **/
@Data
@Schema(description = "部门树查询参数")
public class SysDeptQueryTreeRequest extends QueryRequest {

    /**
     * 关键字
     */
    @Schema(name = "keyWord", description = "关键字", example = "关键字")
    private String keyWord;

    /**
     * 父部门id
     */
    @Schema(description = "父部门ID", example = "101")
    private Long parentId;

    /**
     * 部门编码
     */
    @Schema(description = "部门编码", example = "DEPT001")
    private String deptCode;

    /**
     * 部门名称
     */
    @Schema(description = "部门名称", example = "技术部")
    private String deptName;

    /**
     * 状态（0正常 1停用）
     */
    @Schema(description = "部门状态", example = "0")
    private DeptStatusEnums deptStatus;

    /**
     * 联系电话
     */
    @Schema(description = "联系电话", example = "13800138000")
    private String phone;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱", example = "zhangsan@example.com")
    private String email;
}
