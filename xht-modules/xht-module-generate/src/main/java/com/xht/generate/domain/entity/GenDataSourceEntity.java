package com.xht.generate.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.xht.framework.mybatis.domain.entity.BasicEntity;
import lombok.Data;

/**
 * 代码生成器-数据源配置表
 */
@TableName(value ="gen_data_source")
@Data
public class GenDataSourceEntity extends BasicEntity implements Serializable {
    /**
     * 数据源ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 数据源名称
     */
    private String name;

    /**
     * 数据库类型（MySQL/Oracle）
     */
    private String dbType;

    /**
     * 数据库地址
     */
    private String host;

    /**
     * 端口号
     */
    private String port;

    /**
     * 数据库名称
     */
    private String databaseName;

    /**
     * 用户名
     */
    private String username;

    /**
     * 加密存储的密码（AES加密）
     */
    private String password;

    /**
     * 连接测试结果（success/fail）
     */
    private String testResult;

    /**
     * 最后测试时间
     */
    private LocalDateTime lastTestTime;




}