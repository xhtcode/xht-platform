package com.xht.system.dept.domain.vo;

import com.xht.framework.core.domain.vo.IVO;
import com.xht.system.dept.common.enums.DeptPostStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 部门岗位简单信息
 *
 * @author xht
 **/
@Schema(description = "部门岗位简单信息")
@Data
public class SysDeptPostSimpleVo implements IVO {
    /**
     * 岗位ID
     */
    @Schema(description = "岗位ID")
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
     * 岗位状态（0正常 1停用）
     */
    @Schema(description = "岗位状态")
    private DeptPostStatusEnums postStatus;
}
