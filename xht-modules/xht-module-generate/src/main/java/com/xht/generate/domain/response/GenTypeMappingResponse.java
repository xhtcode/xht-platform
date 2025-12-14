package com.xht.generate.domain.response;

import com.xht.framework.core.domain.response.MetaResponse;
import com.xht.generate.constant.enums.DataBaseTypeEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 字段映射响应信息
 *
 * @author xht
 **/
@Data
@Schema(description = "字段映射响应信息")
public class GenTypeMappingResponse extends MetaResponse {

    /**
     * 映射ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 数据库类型（MySQL/Oracle）
     */
    @Schema(description = "数据库类型")
    private DataBaseTypeEnums dbType;

    /**
     * 数据库数据类型（如：INT/VARCHAR2）
     */
    @Schema(description = "数据库数据类型")
    private String dbDataType;

    /**
     * java类型
     */
    @Schema(description = "java类型")
    private String javaType;

    /**
     * java包类型
     */
    @Schema(description = "java包类型")
    private String importPackage;

    /**
     * ts类型
     */
    @Schema(description = "ts类型")
    private String tsType;
}
