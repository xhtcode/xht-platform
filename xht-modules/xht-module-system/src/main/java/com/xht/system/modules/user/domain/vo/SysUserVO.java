package com.xht.system.modules.user.domain.vo;

import com.xht.framework.core.domain.vo.IVO;
import com.xht.system.modules.dept.domain.response.SysDeptResp;
import com.xht.system.modules.dept.domain.response.SysPostResp;
import com.xht.system.modules.user.domain.response.SysUserProfilesResponse;
import com.xht.system.modules.user.domain.response.SysUserResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 用户信息视图对象响应信息
 *
 * @author xht
 **/
@Data
@Schema(description = "用户信息视图对象响应信息")
public class SysUserVO extends SysUserResponse implements IVO {

    @Schema(description = "用户信息")
    private SysUserProfilesResponse profile;

    /**
     * 用户所在的部门信息
     */
    @Schema(description = "用户所在的部门信息")
    private SysDeptResp dept;

    /**
     * 用户所在的岗位信息
     */
    @Schema(description = "用户所在的岗位信息")
    private List<SysPostResp> post;

}
