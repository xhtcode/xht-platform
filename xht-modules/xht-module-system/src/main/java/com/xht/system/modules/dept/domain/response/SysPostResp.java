package com.xht.system.modules.dept.domain.response;

import com.baomidou.mybatisplus.annotation.TableId;
import com.xht.framework.core.domain.response.BasicResponse;
import com.xht.framework.core.enums.SystemFlagEnums;
import com.xht.system.modules.dept.common.enums.DeptPostStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 部门岗位响应信息
 *
 * @author xht
 **/
@Data
@Schema(description = "部门岗位响应信息")
public class SysPostResp extends BasicResponse {

    /**
     * 岗位ID
     */
    @TableId(value = "岗位ID")
    private Long id;

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
     * 岗位排序
     */
    @Schema(description = "岗位排序")
    private Integer postSort;

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

    /**
     * 岗位描述
     */
    @Schema(description = "岗位描述")
    private String remark;

}
