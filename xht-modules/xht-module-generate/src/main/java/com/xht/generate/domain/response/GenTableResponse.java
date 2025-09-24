package com.xht.generate.domain.response;

import com.baomidou.mybatisplus.annotation.TableId;
import com.xht.framework.core.domain.response.BasicResponse;
import com.xht.generate.constant.enums.DataBaseTypeEnums;
import com.xht.generate.constant.enums.PageStyleEnums;
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
public class GenTableResponse extends BasicResponse {

    /**
     * 表ID
     */
    @TableId(value = "表ID")
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
     * 数据库表名
     */
    @Schema(description = "数据库表名")
    private String tableName;

    /**
     * 表注释
     */
    @Schema(description = "表注释")
    private String tableComment;

    /**
     * 模块名称
     */
    @Schema(description = "模块名称")
    private String moduleName;

    /**
     * 业务名称
     */
    @Schema(description = "业务名称")
    private String serviceName;

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
     * 后端作者
     */
    @Schema(description = "后端作者")
    private String backEndAuthor;

    /**
     * 前端作者
     */
    @Schema(description = "前端作者")
    private String frontEndAuthor;

    /**
     * 请求前缀
     */
    @Schema(description = "请求前缀")
    private String urlPrefix;

    /**
     * 权限前缀
     */
    @Schema(description = "权限前缀")
    private String permissionPrefix;

    /**
     * 上级菜单
     */
    @Schema(description = "上级菜单")
    private Long parentMenuId;

    /**
     * 页面风格
     */
    @Schema(description = "页面风格")
    private PageStyleEnums pageStyle;

    /**
     * 页面宽度
     */
    @Schema(description = "页面宽度")
    private Integer pageStyleWidth;

    /**
     * 每行数量
     */
    @Schema(description = "每行数量")
    private Integer fromNumber;

    /**
     * 表创建时间
     */
    @Schema(description = "表创建时间")
    private LocalDateTime tableCreateTime;

    /**
     * 表更新时间
     */
    @Schema(description = "表更新时间")
    private LocalDateTime tableUpdateTime;


}
