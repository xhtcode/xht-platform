package com.xht.generate.domain.request;

import com.xht.framework.core.domain.request.PageQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 表信息分页查询参数
 *
 * @author xht
 **/
@Data
@Schema(description = "表信息分页查询参数")
public class GenTableInfoQueryRequest extends PageQueryRequest {

    /**
     * 分组id
     */
    @Schema(description = "分组id")
    private Long groupId;

    /**
     * 数据源ID
     */
    @Schema(description = "数据源ID")
    private Long dataSourceId;

    /**
     * 数据库表名
     */
    @Schema(description = "数据库表名")
    private String tableName;

    /**
     * 表注释（如：用户表）
     */
    @Schema(description = "表注释")
    private String tableComment;

}
