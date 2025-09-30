package com.xht.generate.domain.query;

import com.xht.framework.core.domain.request.PageQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 数据源分页查询参数
 *
 * @author xht
 **/
@Data
@Schema(description = "数据源分页查询参数")
public class GenDataSourceQuery extends PageQueryRequest {

    /**
     * 数据源名称
     */
    @Schema(description = "数据源名称")
    private String name;

    /**
     * 数据库类型（MySQL/Oracle）
     */
    @Schema(description = "数据库类型")
    private String dbType;

}
