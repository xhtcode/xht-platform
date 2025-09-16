package com.xht.generate.domain.query;

import com.xht.framework.core.domain.request.PageQueryRequest;
import com.xht.generate.constant.enums.DataBaseTypeEnums;
import com.xht.generate.constant.enums.LanguageTypeEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 字段映射分页查询参数
 *
 * @author xht
 **/
@Data
@Schema(description = "字段映射分页查询参数")
public class GenTypeMappingQueryRequest extends PageQueryRequest {

    /**
     * 数据库类型（MySQL/Oracle）
     */
    @Schema(description = "数据库类型")
    private DataBaseTypeEnums dbType;

    /**
     * 目标编程语言（Java/TypeScript）
     */
    @Schema(description = "目标编程语言")
    private LanguageTypeEnums targetLanguage;

}
