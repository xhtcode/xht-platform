package com.xht.platform.file.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 描述： 文件模块运行时配置实体
 * <p>全局策略，管理端可编辑；支持标记是否允许用户做文件级覆盖</p>
 *
 * @author xht
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_file_setting")
public class SysFileSettingEntity extends BasicEntity {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 配置键
     */
    @TableField(value = "setting_key")
    private String settingKey;

    /**
     * 当前值
     */
    @TableField(value = "setting_value")
    private String settingValue;

    /**
     * 值类型：string/int/bool/json
     */
    @TableField(value = "value_type")
    private String valueType;

    /**
     * 分组
     */
    @TableField(value = "setting_group")
    private String settingGroup;

    /**
     * 配置名称
     */
    @TableField(value = "setting_name")
    private String settingName;

    /**
     * 说明
     */
    @TableField(value = "description")
    private String description;

    /**
     * 默认值
     */
    @TableField(value = "default_value")
    private String defaultValue;

    /**
     * 最小值
     */
    @TableField(value = "min_value")
    private String minValue;

    /**
     * 最大值
     */
    @TableField(value = "max_value")
    private String maxValue;

    /**
     * 是否允许用户文件级覆盖：1 是
     */
    @TableField(value = "user_overridable")
    private Integer userOverridable;

    /**
     * 覆盖方向：LOCKED/TIGHTEN_ONLY/FREE
     */
    @TableField(value = "override_direction")
    private String overrideDirection;

    /**
     * 用户覆盖最小值
     */
    @TableField(value = "override_min")
    private String overrideMin;

    /**
     * 用户覆盖最大值
     */
    @TableField(value = "override_max")
    private String overrideMax;

    /**
     * 用户覆盖枚举，逗号分隔
     */
    @TableField(value = "override_options")
    private String overrideOptions;

    /**
     * 覆盖提示
     */
    @TableField(value = "override_hint")
    private String overrideHint;

    /**
     * 是否可在管理端编辑：1 是
     */
    @TableField(value = "editable")
    private Integer editable;

    /**
     * 乐观锁版本
     */
    @TableField(value = "version")
    private Integer version;

}
