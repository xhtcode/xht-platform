package com.xht.modules.admin.system.domain.vo;

import com.xht.modules.admin.system.domain.response.LeaderPostResponse;
import com.xht.modules.admin.system.domain.response.LeaderUserResponse;
import com.xht.modules.admin.system.domain.response.SysDeptResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


/**
 * 部门响应信息
 *
 * @author xht
 */
@Data
@Schema(description = "部门响应信息")
public class SysDeptVo extends SysDeptResponse {

    /**
     * 负责人职位信息
     */
    @Schema(description = "负责人职位信息")
    private LeaderPostResponse leaderPost;

    /**
     * 负责人用户信息
     */
    @Schema(description = "负责人用户信息")
    private LeaderUserResponse leaderUser;

}
