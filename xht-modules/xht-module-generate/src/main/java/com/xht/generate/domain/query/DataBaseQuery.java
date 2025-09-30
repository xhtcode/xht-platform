package com.xht.generate.domain.query;

import com.xht.framework.core.domain.request.PageQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 数据库查询
 *
 * @author xht
 **/
@Data
@Schema(description = "数据库拆线呢参数")
public class DataBaseQuery extends PageQueryRequest {

    /**
     * 表名
     */
    @Schema(description = "表名")
    private String tableName;

    /**
     * 数据源id
     */
    @NotNull(message = "数据源id不能为空")
    @Schema(description = "数据源id")
    private Long dataSourceId;
}
