package com.xht.system.user.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.xht.framework.core.domain.vo.IVO;
import com.xht.system.dept.common.enums.DeptPostStatusEnums;
import com.xht.system.dept.common.enums.DeptStatusEnums;
import com.xht.system.user.domain.response.SysUserProfilesResponse;
import com.xht.system.user.domain.response.SysUserResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用户信息视图对象
 *
 * @author xht
 **/
@Data
@Schema(title = "用户信息视图对象")
public class SysUserVO extends SysUserResponse implements IVO {

    private SysUserProfilesResponse profile;
    /**
     * 岗位ID
     */
    @TableId(value = "岗位ID")
    private Long postId;

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
     * 父部门id
     */
    @Schema(description = "父部门ID", example = "101")
    private Long deptParentId;

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
}
