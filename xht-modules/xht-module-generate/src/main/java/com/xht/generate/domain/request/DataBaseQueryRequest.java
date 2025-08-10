package com.xht.generate.domain.request;

import com.xht.framework.core.domain.request.PageQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 数据库查询
 *
 * @author xht
 **/
@Data
@Schema(description = "数据库拆线呢参数")
public class DataBaseQueryRequest extends PageQueryRequest {

    /**
     * 表名
     */
    @Schema(description = "表名")
    private String tableName;

}
