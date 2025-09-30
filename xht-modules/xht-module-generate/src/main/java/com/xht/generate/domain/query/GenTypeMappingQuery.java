package com.xht.generate.domain.query;

import com.xht.framework.core.domain.request.PageQueryRequest;
import com.xht.generate.constant.enums.DataBaseTypeEnums;
import com.xht.generate.constant.enums.LanguageTypeEnums;
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
public class GenTypeMappingQuery extends PageQueryRequest {

    /**
     * 数据库类型（MySQL/Oracle）
     */
    @NotNull(message = "数据库类型不能为空")
    @Schema(description = "数据库类型")
    private DataBaseTypeEnums dbType;

    /**
     * 目标编程语言（Java/TypeScript）
     */
    @NotNull(message = "数据库类型不能为空")
    @Schema(description = "目标编程语言")
    private LanguageTypeEnums targetLanguage;

}
