package com.xht.platform.file.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import com.xht.platform.file.enums.FileAccessScopeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 描述： 文件用途配置实体
 * <p>替代硬编码 FileUsedEnum，管理端可新增/停用用途并配置业务策略</p>
 *
 * @author xht
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_file_used")
public class SysFileUsedEntity extends BasicEntity {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用途编码，创建后不可改
     */
    @TableField(value = "used_code")
    private String usedCode;

    /**
     * 用途名称
     */
    @TableField(value = "used_name")
    private String usedName;

    /**
     * 说明
     */
    @TableField(value = "description")
    private String description;

    /**
     * 是否内置：1 是
     */
    @TableField(value = "built_in")
    private Integer builtIn;

    /**
     * 是否启用：1 是
     */
    @TableField(value = "enabled")
    private Integer enabled;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 默认访问范围
     */
    @TableField(value = "access_scope")
    private FileAccessScopeEnum accessScope;

    /**
     * 是否允许设为 public：1 是
     */
    @TableField(value = "public_allowed")
    private Integer publicAllowed;

    /**
     * 下载闸门：1 允许
     */
    @TableField(value = "download_allowed")
    private Integer downloadAllowed;

    /**
     * 打印闸门：1 允许
     */
    @TableField(value = "print_allowed")
    private Integer printAllowed;

    /**
     * 配置版本
     */
    @TableField(value = "config_version")
    private Integer configVersion;

}
