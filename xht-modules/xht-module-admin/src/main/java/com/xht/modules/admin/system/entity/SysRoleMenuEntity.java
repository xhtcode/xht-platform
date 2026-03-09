package com.xht.modules.admin.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.xht.framework.mybatis.domain.entity.Entity;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 角色-菜单关联表
 *
 * @author xht
 */
@Data
@TableName(value = "sys_role_menu")
public class SysRoleMenuEntity extends Entity implements Serializable {

    /**
     * 关联id
     */
    @TableId(value = "role_id", type = IdType.ASSIGN_ID)
    private Long roleId;

    /**
     * 菜单id
     */
    @TableField(value = "menu_id")
    private Long menuId;


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