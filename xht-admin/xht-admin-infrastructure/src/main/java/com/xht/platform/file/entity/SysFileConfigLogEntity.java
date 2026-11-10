package com.xht.platform.file.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 描述： 文件配置变更日志实体
 *
 * @author xht
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_file_config_log")
public class SysFileConfigLogEntity extends Entity {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 范围：GLOBAL/TYPE/USED/FILE
     */
    @TableField(value = "scope")
    private String scope;

    /**
     * 目标ID：文件ID或用途/后缀
     */
    @TableField(value = "target_id")
    private String targetId;

    /**
     * 配置键
     */
    @TableField(value = "setting_key")
    private String settingKey;

    /**
     * 旧值
     */
    @TableField(value = "old_value")
    private String oldValue;

    /**
     * 新值
     */
    @TableField(value = "new_value")
    private String newValue;

    /**
     * 操作人
     */
    @TableField(value = "operator")
    private String operator;

    /**
     * 操作IP
     */
    @TableField(value = "operator_ip")
    private String operatorIp;

    /**
     * 创建人
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
