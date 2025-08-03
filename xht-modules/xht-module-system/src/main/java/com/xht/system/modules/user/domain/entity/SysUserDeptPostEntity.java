package com.xht.system.modules.user.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.xht.framework.mybatis.domain.entity.Entity;
import com.xht.system.modules.user.common.enums.PositionNatureEnums;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户部门关联表
 *
 * @author xht
 */
@Data
@TableName(value = "sys_user_dept_post_post")
public class SysUserDeptPostEntity extends Entity implements Serializable {
    /**
     * 用户ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 职位性质
     */
    @TableField(value = "position_nature")
    private PositionNatureEnums positionNature;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 部门id
     */
    @TableField(value = "dept_id")
    private Long deptId;

    /**
     * 岗位id
     */
    @TableField(value = "post_id")
    private Long postId;

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