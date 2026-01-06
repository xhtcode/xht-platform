package com.xht.modules.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import com.xht.framework.core.enums.UserStatusEnums;
import com.xht.framework.core.enums.UserTypeEnums;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户认证表
 *
 * @author xht
 */
@Data
@TableName(value = "sys_user")
public class SysUserEntity extends BasicEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户类型
     */
    @TableField(value = "user_type")
    private UserTypeEnums userType;

    /**
     * 用户账号
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 用户昵称
     */
    @TableField(value = "nick_name")
    private String nickName;

    /**
     * 用户密码
     */
    @TableField(value = "pass_word")
    private String passWord;

    /**
     * 密码盐值
     */
    @TableField(value = "pass_word_salt")
    private String passWordSalt;

    /**
     * 账号状态
     */
    @TableField(value = "user_status")
    private UserStatusEnums userStatus;

    /**
     * 手机号码
     */
    @TableField(value = "user_phone")
    private String userPhone;

    /**
     * 头像地址
     */
    @TableField(value = "user_avatar")
    private String userAvatar;

    /**
     * 部门id
     */
    @TableField(value = "dept_id")
    private Long deptId;

    /**
     * 部门名称
     */
    @TableField(value = "dept_name")
    private String deptName;

}