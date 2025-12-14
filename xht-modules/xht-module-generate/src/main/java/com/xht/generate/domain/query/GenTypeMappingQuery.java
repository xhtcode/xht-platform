package com.xht.generate.domain.query;

import com.xht.framework.core.domain.query.PageBasicQuery;
import com.xht.generate.constant.enums.DataBaseTypeEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 字段映射分页查询参数
 *
 * @author xht
 **/
@Data
@Schema(description = "字段映射分页查询参数")
public class GenTypeMappingQuery extends PageBasicQuery {

    /**
     * 数据库类型（MySQL/Oracle）
     */
    @NotNull(message = "数据库类型不能为空")
    @Schema(description = "数据库类型")
    private DataBaseTypeEnums dbType;

    /**
     * 数据库数据类型（如：INT/VARCHAR2）
     */
    @Schema(description = "数据库数据类型")
    private String dbDataType;

}
