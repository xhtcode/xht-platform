package com.xht.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import com.xht.api.system.enums.RoleStatusEnums;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色表
 *
 * @author xht
 */
@Data
@TableName(value = "sys_role")
public class SysRoleEntity extends BasicEntity implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 角色编码
     */
    @TableField(value = "role_code")
    private String roleCode;

    /**
     * 角色名称
     */
    @TableField(value = "role_name")
    private String roleName;

    /**
     * 数据范围（1全部数据权限 2自定数据权限 3本部门数据权限 4本部门及以下数据权限 5本岗位数据权限  6仅本人数据权限）
     */
    @TableField(value = "data_scope")
    private Integer dataScope;

    /**
     * 状态（0正常 1停用）
     */
    @TableField(value = "role_status")
    private RoleStatusEnums roleStatus;

    /**
     * 显示顺序
     */
    @TableField(value = "role_sort")
    private Integer roleSort;

    /**
     * 角色描述
     */
    @TableField(value = "remark")
    private String remark;

}