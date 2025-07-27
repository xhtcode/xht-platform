package com.xht.system.modules.user.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import com.xht.system.modules.user.common.enums.UserStatusEnums;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户认证表
 *
 * @author xht
 */
@Data
@TableName(value = "sys_user")
public class SysUserEntity extends BasicEntity implements Serializable {
    /**
     * 用户ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 密码
     */
    @TableField(value = "pass_word")
    private String passWord;

    /**
     * 密码盐值
     */
    @TableField(value = "salt")
    private String salt;

    /**
     * 用户昵称
     */
    @TableField(value = "nick_name")
    private String nickName;

    /**
     * 手机号
     */
    @TableField(value = "mobile")
    private String mobile;

    /**
     * 头像地址
     */
    @TableField(value = "avatar_url")
    private String avatarUrl;

    /**
     * 账号状态(1-正常,2-锁定,3-禁用,4-过期)
     */
    @TableField(value = "user_status")
    private UserStatusEnums userStatus;

    /**
     * 部门id
     */
    @TableField(value = "dept_id")
    private Long deptId;
}