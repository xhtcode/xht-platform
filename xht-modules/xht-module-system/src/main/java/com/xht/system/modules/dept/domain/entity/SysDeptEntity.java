package com.xht.system.modules.dept.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import com.xht.system.modules.dept.common.enums.DeptStatusEnums;
import lombok.Data;

import java.io.Serializable;

/**
 * 部门信息表
 *
 * @author xht
 */
@TableName(value = "sys_dept")
@Data
public class SysDeptEntity extends BasicEntity implements Serializable {
    /**
     * 部门id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 父部门id
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 部门编码
     */
    @TableField(value = "dept_code")
    private String deptCode;

    /**
     * 部门名称
     */
    @TableField(value = "dept_name")
    private String deptName;

    /**
     * 树层级
     */
    @TableField(value = "dept_level")
    private Integer deptLevel;

    /**
     * 状态（0正常 1停用）
     */
    @TableField(value = "dept_status")
    private DeptStatusEnums deptStatus;

    /**
     * 显示顺序
     */
    @TableField(value = "dept_sort")
    private Integer deptSort;

    /**
     * 祖级列表
     */
    @TableField(value = "ancestors")
    private String ancestors;

    /**
     * 负责人用户ID
     */
    @TableField(value = "leader_user_id")
    private Long leaderUserId;
    /**
     * 负责人名称
     */
    @TableField(value = "leader_name")
    private String leaderName;

    /**
     * 负责人岗位ID
     */
    @TableField(value = "leader_post_id")
    private Long leaderPostId;


    /**
     * 联系电话
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

}