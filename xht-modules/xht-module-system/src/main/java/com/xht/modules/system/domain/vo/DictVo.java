package com.xht.modules.system.domain.vo;

import com.xht.framework.core.domain.vo.IVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 字典项视图对象响应信息
 */
@Data
@Schema(description = "字典项视图对象响应信息")
public class DictVo implements IVO {

    /**
     * 字典项标签
     */
    @Schema(description = "字典项标签")
    private String label;

    /**
     * 字典项值
     */
    @Schema(description = "字典项值")
    private String value;

    /**
     * 显示颜色
     */
    @Schema(description = "显示颜色")
    private String color;


    /**
     * 字典项是否禁用
     */
    @Schema(description = "字典项是否禁用")
    private Boolean disabled;

}