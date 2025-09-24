package com.xht.generate.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import com.xht.generate.constant.enums.DataBaseTypeEnums;
import com.xht.generate.constant.enums.PageStyleEnums;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 代码生成器-表结构信息表
 * @author xht
 */
@Data
@TableName(value = "gen_table")
public class GenTableEntity extends BasicEntity implements Serializable {

    /**
     * 表ID
     */
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 分组id
     */
    @TableField(value = "group_id")
    private Long groupId;

    /**
     * 数据源ID
     */
    @TableField(value = "data_source_id")
    private Long dataSourceId;

    /**
     * 数据库类型
     */
    @TableField(value = "data_base_type")
    private DataBaseTypeEnums dataBaseType;

    /**
     * 引擎名称
     */
    @TableField(value = "engine_name")
    private String engineName;

    /**
     * 数据库表名
     */
    @TableField(value = "table_name")
    private String tableName;

    /**
     * 表注释
     */
    @TableField(value = "table_comment")
    private String tableComment;

    /**
     * 模块名称
     */
    @TableField(value = "module_name")
    private String moduleName;

    /**
     * 业务名称
     */
    @TableField(value = "service_name")
    private String serviceName;

    /**
     * 代码名称
     */
    @TableField(value = "code_name")
    private String codeName;

    /**
     * 代码注释
     */
    @TableField(value = "code_comment")
    private String codeComment;

    /**
     * 后端作者
     */
    @TableField(value = "back_end_author")
    private String backEndAuthor;

    /**
     * 前端作者
     */
    @TableField(value = "front_end_author")
    private String frontEndAuthor;

    /**
     * 请求前缀
     */
    @TableField(value = "url_prefix")
    private String urlPrefix;

    /**
     * 权限前缀
     */
    @TableField(value = "permission_prefix")
    private String permissionPrefix;

    /**
     * 上级菜单
     */
    @TableField(value = "parent_menu_id")
    private Long parentMenuId;

    /**
     * 页面风格
     */
    @TableField(value = "page_style")
    private PageStyleEnums pageStyle;

    /**
     * 页面宽度
     */
    @TableField(value = "page_style_width")
    private Integer pageStyleWidth;

    /**
     * 每行数量
     */
    @TableField(value = "from_number")
    private Integer fromNumber;

    /**
     * 表创建时间
     */
    @TableField(value = "table_create_time")
    private LocalDateTime tableCreateTime;

    /**
     * 表更新时间
     */
    @TableField(value = "table_update_time")
    private LocalDateTime tableUpdateTime;

}