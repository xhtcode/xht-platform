package com.xht.demo.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@TableName(value = "users")
@Data
public class Users implements Serializable {
    /**
     * 用户名字段，作为主键标识用户
     */
    @TableId
    private String username;

    /**
     * 密码字段，存储用户的登录密码
     */
    private String password;

    /**
     * 启用状态字段，表示用户账户是否启用
     */
    private Integer enabled;


    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = 1L;
}