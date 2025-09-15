package com.xht.generate.domain.response;

import com.xht.framework.core.domain.response.BasicResponse;
import com.xht.generate.constant.enums.DataBaseTypeEnums;
import com.xht.generate.domain.TableExtConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 表信息响应信息
 *
 * @author xht
 **/
@Data
@Schema(description = "表信息响应信息")
public class GenTableInfoResponse extends BasicResponse {

    /**
     * 表ID
     */
    @Schema(description = "表ID")
    private String id;

    /**
     * 模板分组id
     */
    @Schema(description = "模板分组id")
    private Long groupId;

    /**
     * 数据源ID
     */
    @Schema(description = "数据源ID")
    private Long dataSourceId;

    /**
     * 数据库类型
     */
    @Schema(description = "数据库类型")
    private DataBaseTypeEnums dataBaseType;

    /**
     * 引擎名称
     */
    @Schema(description = "引擎名称")
    private String engineName;

    /**
     * 表名
     */
    @Schema(description = "表名")
    private String tableName;

    /**
     * 表注释（如：用户表）
     */
    @Schema(description = "表注释")
    private String tableComment;

    /**
     * 代码名称（如：User）
     */
    @Schema(description = "代码名称")
    private String codeName;

    /**
     * 代码注释（如：用户）
     */
    @Schema(description = "代码注释")
    private String codeComment;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime tableCreateTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime tableUpdateTime;

    /**
     * 配置属性
     */
    @Schema(description = "配置属性")
    private TableExtConfig extConfig;

}
