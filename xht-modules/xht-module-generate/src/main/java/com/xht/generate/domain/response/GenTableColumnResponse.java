package com.xht.generate.domain.response;

import com.xht.framework.core.domain.response.MetaResponse;
import com.xht.generate.constant.enums.GenStatusEnums;
import com.xht.generate.constant.enums.IdPrimaryKeyEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 字段信息响应信息
 *
 * @author xht
 **/
@Data
@Schema(description = "字段信息响应信息")
public class GenTableColumnResponse extends MetaResponse {

    /**
     * 主键
     */
    @Schema(description = "主键")
    private Long id;

    /**
     * 表id
     */
    @Schema(description = "表id")
    private Long tableId;

    /**
     * 表名称(冗余字段)
     */
    @Schema(description = "表名称")
    private String tableName;

    /**
     * 字段名
     */
    @Schema(description = "字段名")
    private String dbName;

    /**
     * 字段类型
     */
    @Schema(description = "字段类型")
    private String dbType;

    /**
     * 字段主键：0-非主键，1-主键
     */
    @Schema(description = "字段主键：0-非主键，1-主键")
    private IdPrimaryKeyEnums dbPrimary;

    /**
     * 字段必填：0-非必填，1-必填
     */
    @Schema(description = "字段必填：0-非必填，1-必填")
    private GenStatusEnums dbRequired;

    /**
     * 字段注释
     */
    @Schema(description = "字段注释")
    private String dbComment;

    /**
     * 字段长度
     */
    @Schema(description = "字段长度")
    private int dbLength;

    /**
     * 代码名称
     */
    @Schema(description = "代码名称")
    private String codeName;

    /**
     * 代码注释
     */
    @Schema(description = "代码注释")
    private String codeComment;

    /**
     * 表单新增：0-不显示，1-显示
     */
    @Schema(description = "表单新增")
    private GenStatusEnums fromInsert;

    /**
     * 表单更新：0-不显示，1-显示
     */
    @Schema(description = "表单更新")
    private GenStatusEnums fromUpdate;

    /**
     * 表单输入长度
     */
    @Schema(description = "表单输入长度")
    private Integer fromLength;

    /**
     * 表单必填：0-非必填，1-必填
     */
    @Schema(description = "表单必填：0-非必填，1-必填")
    private GenStatusEnums fromFill;

    /**
     * 表单组件
     */
    @Schema(description = "表单组件")
    private String fromComponent;

    /**
     * 列表显示：0-不显示，1-显示
     */
    @Schema(description = "列表显示：0-不显示，1-显示")
    private GenStatusEnums listShow;

    /**
     * 列表描述
     */
    @Schema(description = "列表描述")
    private String listComment;

    /**
     * 显示切换禁用：0-不禁用，1-禁用
     */
    @Schema(description = "显示切换禁用：0-不禁用，1-禁用")
    private GenStatusEnums listDisabled;

    /**
     * 默认隐藏：0-不隐藏，1-隐藏
     */
    @Schema(description = "默认隐藏：0-不隐藏，1-隐藏")
    private GenStatusEnums listHidden;

    /**
     * java类型
     */
    @Schema(description = "java类型")
    private String codeJava;

    /**
     * java类型 包地址
     */
    @Schema(description = "java类型 包地址")
    private String codeJavaPackage;

    /**
     * ts类型
     */
    @Schema(description = "ts类型")
    private String codeTs;

    /**
     * 字段排序
     */
    @Schema(description = "字段排序")
    private Integer sortOrder;

}
