package com.xht.system.modules.dept.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xht.framework.core.domain.vo.IVO;
import com.xht.system.modules.dept.domain.response.SysPostResponse;
import com.xht.system.modules.dept.domain.response.SysDeptResponse;
import com.xht.system.modules.user.common.enums.PositionNatureEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 部门岗位Vo响应信息
 *
 * @author xht
 **/
@Data
@Schema(description = "部门岗位响应信息")
public class SysDeptPostVo implements IVO {

    /**
     * index
     */
    @JsonIgnore
    @Schema(description = "序号")
    private Long index;

    /**
     * 职位性质
     */
    @Schema(description = "职位性质")
    private PositionNatureEnums positionNature;

    /**
     * 部门信息
     */
    @Schema(description = "部门信息")
    private SysDeptResponse dept;

    /**
     * 岗位信息
     */
    @Schema(description = "岗位信息")
    private SysPostResponse post;

}
