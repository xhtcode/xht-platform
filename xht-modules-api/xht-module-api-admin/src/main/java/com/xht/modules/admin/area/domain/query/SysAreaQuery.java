package com.xht.modules.admin.area.domain.query;

import com.xht.framework.core.domain.query.PageBasicQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 行政区划代码 查询参数
 *
 * @author xht
 **/
@Data
@Schema(description = "行政区划代码 查询参数")
public class SysAreaQuery extends PageBasicQuery {

    /**
     * 区划编码
     */
    @Schema(description = "区划编码")
    private String areaCode;

    /**
     * 区划名称
     */
    @Schema(description = "区划名称")
    private String areaName;

}
