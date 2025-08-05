package com.xht.system.modules.dept.domain.response;

import com.xht.framework.core.domain.response.BasicResponse;
import com.xht.system.modules.dept.common.enums.DeptStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 部门响应信息
 *
 * @author xht
 */
@Data
@Schema(description = "部门响应信息")
public class SysDeptResponse extends BasicResponse {
    /**
     * 部门id
     */
    @Schema(description = "部门id", example = "101")
    private Long id;

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
     * 树层级
     */
    @Schema(description = "树层级", example = "1")
    private Integer deptLevel;

    /**
     * 状态（0正常 1停用）
     */
    @Schema(description = "部门状态", example = "0")
    private DeptStatusEnums deptStatus;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序", example = "1")
    private Integer deptSort;

    /**
     * 祖级列表
     */
    @Schema(description = "祖先列表", example = "0,1,101")
    private String ancestors;

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

    /**
     * 备注
     */
    @Schema(description = "备注", example = "这是一个技术部门")
    private String remark;

}