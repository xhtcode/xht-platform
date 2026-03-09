package com.xht.modules.admin.system.domain.response;

import com.xht.framework.core.domain.response.BasicResponse;
import com.xht.modules.admin.system.enums.DeptPostStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 部门负责人职位信息
 *
 * @author xht
 **/
@Data
@Schema(description = "部门负责人职位信息")
public class LeaderPostResponse extends BasicResponse {

    /**
     * 岗位类型
     */
    @Schema(description = "岗位类型")
    private String postType;

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
     * 岗位描述
     */
    @Schema(description = "岗位描述")
    private String remark;

}
