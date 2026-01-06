package com.xht.modules.system.domain.vo;

import com.xht.framework.core.domain.vo.IVO;
import com.xht.modules.common.enums.DeptPostStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 部门岗位简单响应信息
 *
 * @author xht
 **/
@Data
@Schema(description = "部门岗位简单响应信息")
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
