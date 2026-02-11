package com.xht.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users implements Serializable {
    /**
     * 用户名字段，作为主键标识用户
     */
    private String username;

    /**
     * 密码字段，存储用户的登录密码
     */
    private String password;
}