package com.xht.platform.common.dict.domain;

import com.xht.framework.common.domain.vo.XhtVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 字典项视图对象响应信息
 */
@Data
@Schema(description = "字典项视图对象响应信息")
public class DictVO implements XhtVO {

    /**
     * 字典项名称
     */
    @Schema(description = "字典项名称")
    private String label;

    /**
     * 字典项编码
     */
    @Schema(description = "字典项编码")
    private String value;

    /**
     * 颜色显示
     */
    @Schema(description = "颜色显示")
    private String color;


    /**
     * 字典项是否禁用
     */
    @Schema(description = "字典项是否禁用")
    private Boolean disabled;

}