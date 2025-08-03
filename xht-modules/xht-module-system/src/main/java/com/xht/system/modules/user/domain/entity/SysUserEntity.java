package com.xht.system.modules.user.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
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
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户类型
     */
    @TableField(value = "user_type")
    private Integer userType;

    /**
     * 用户账号
     */
    @TableField(value = "user_account")
    private String userAccount;

    /**
     * 用户昵称
     */
    @TableField(value = "user_name")
    private String userName;

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
     * 手机号码
     */
    @TableField(value = "phone_number")
    private String phoneNumber;

    /**
     * 头像地址
     */
    @TableField(value = "avatar_url")
    private String avatarUrl;

    /**
     * 账号状态
     */
    @TableField(value = "user_status")
    private Integer userStatus;

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

}