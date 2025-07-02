package com.xht.system.dept.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.xht.framework.core.domain.vo.IVO;
import com.xht.system.dept.common.enums.DeptPostStatusEnums;
import com.xht.system.dept.domain.response.SysDeptResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 部门岗位Vo响应信息
 *
 * @author xht
 **/
@Data
@Schema(description = "部门岗位响应信息")
public class SysDeptPostVo extends SysDeptResponse implements IVO {

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
     * 岗位描述
     */
    @Schema(description = "岗位描述")
    private String postRemark;

}
