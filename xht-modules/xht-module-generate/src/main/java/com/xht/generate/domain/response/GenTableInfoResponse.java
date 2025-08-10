package com.xht.generate.domain.response;

import com.xht.framework.core.domain.response.BasicResponse;
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
    private Long id;

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
     * 引擎名称
     */
    @Schema(description = "引擎名称")
    private String engineName;

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

    /**
     * 生成的类名（如：User）
     */
    @Schema(description = "生成的类名")
    private String codeName;

    /**
     * 代码的注释（如：用户）
     */
    @Schema(description = "代码的注释")
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
}
