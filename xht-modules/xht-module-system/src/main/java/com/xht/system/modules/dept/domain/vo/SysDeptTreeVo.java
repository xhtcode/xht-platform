package com.xht.system.modules.dept.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xht.framework.core.domain.vo.IVO;
import com.xht.system.modules.dept.domain.response.SysDeptResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 部门树响应信息
 *
 * @author xht
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(description = "部门树响应信息")
public class SysDeptTreeVo extends SysDeptResponse implements IVO {

    /**
     * 子部门列表
     */
    @Schema(description = "子部门列表")
    private List<SysDeptTreeVo> children;
}
