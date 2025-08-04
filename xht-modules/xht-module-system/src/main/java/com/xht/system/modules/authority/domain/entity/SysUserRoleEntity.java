package com.xht.system.modules.authority.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.Entity;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户角色关联表
 *
 * @author xht
 */
@Data
@TableName(value = "sys_user_role")
public class SysUserRoleEntity extends Entity {

    /**
     * 用户id
     */
    @TableId(value = "user_id")
    private Long userId;

    /**
     * 角色id
     */
    @TableField(value = "role_id")
    private Long roleId;


    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    /**
     * 创建用户
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;
}