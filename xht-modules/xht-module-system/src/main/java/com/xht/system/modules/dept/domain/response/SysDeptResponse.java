package com.xht.system.modules.dept.domain.response;

import com.xht.framework.core.domain.response.MetaResponse;
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
public class SysDeptResponse extends MetaResponse {
    /**
     * 部门id
     */
    @Schema(description = "部门id")
    private Long id;

    /**
     * 父部门id
     */
    @Schema(description = "父部门ID")
    private Long parentId;

    /**
     * 部门编码
     */
    @Schema(description = "部门编码")
    private String deptCode;

    /**
     * 部门名称
     */
    @Schema(description = "部门名称")
    private String deptName;

    /**
     * 树层级
     */
    @Schema(description = "树层级")
    private Integer deptLevel;

    /**
     * 状态（0正常 1停用）
     */
    @Schema(description = "部门状态")
    private DeptStatusEnums deptStatus;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer deptSort;

    /**
     * 祖级列表
     */
    @Schema(description = "祖先列表")
    private String ancestors;

    /**
     * 联系电话
     */
    @Schema(description = "联系电话")
    private String phone;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    private String email;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

}