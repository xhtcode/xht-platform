package com.xht.generate.domain.response;

import com.xht.framework.core.domain.response.BasicResponse;
import com.xht.generate.constant.enums.GenStatusEnums;
import com.xht.generate.domain.ColumnExtConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 字段信息响应信息
 *
 * @author xht
 **/
@Data
@Schema(description = "字段信息响应信息")
public class GenTableColumnResponse extends BasicResponse {

    /**
     * 字段ID
     */
    @Schema(description = "id")
    private Long id;

    /**
     * 表ID（关联gen_table_info）
     */
    @Schema(description = "表ID")
    private String tableId;

    /**
     * 数据库字段名
     */
    @Schema(description = "数据库字段名")
    private String columnName;

    /**
     * 数据库字段类型
     */
    @Schema(description = "数据库字段类型")
    private String dbDataType;

    /**
     * 字段注释
     */
    @Schema(description = "字段注释")
    private String columnComment;

    /**
     * 字段长度
     */
    @Schema(description = "字段长度")
    private int columnLength;

    /**
     * 字段代码名称
     */
    @Schema(description = "字段代码名称")
    private String codeName;

    /**
     * 字段代码注释
     */
    @Schema(description = "字段代码注释")
    private String codeComment;

    /**
     * 是否必填（0否 1是）
     */
    @Schema(description = "是否必填")
    private GenStatusEnums isRequired;

    /**
     * 是否主键（0否 1是）
     */
    @Schema(description = "是否主键")
    private GenStatusEnums isPrimary;

    /**
     * 配置属性
     */
    @Schema(description = "配置属性")
    private ColumnExtConfig extConfig;

    /**
     * 字段排序
     */
    @Schema(description = "字段排序")
    private Integer sortOrder;

}
