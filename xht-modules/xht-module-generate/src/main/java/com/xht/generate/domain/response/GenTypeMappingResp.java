package com.xht.generate.domain.response;

import com.xht.framework.core.domain.response.BasicResponse;
import com.xht.generate.constant.enums.DataBaseTypeEnums;
import com.xht.generate.constant.enums.LanguageTypeEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 字段映射响应信息
 *
 * @author xht
 **/
@Data
@Schema(description = "字段映射响应信息")
public class GenTypeMappingResp extends BasicResponse {

    /**
     * 映射ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 数据库类型（MySQL/Oracle）
     */
    @Schema(description = "db_type")
    private DataBaseTypeEnums dbType;

    /**
     * 数据库数据类型（如：INT/VARCHAR2）
     */
    @Schema(description = "db_data_type")
    private String dbDataType;

    /**
     * 目标编程语言（Java/TypeScript）
     */
    @Schema(description = "target_language")
    private LanguageTypeEnums targetLanguage;

    /**
     * 目标语言数据类型（如：Integer/number）
     */
    @Schema(description = "target_data_type")
    private String targetDataType;

    /**
     * 导入包路径（如：java.time.LocalDateTime）
     */
    @Schema(description = "import_package")
    private String importPackage;

}
