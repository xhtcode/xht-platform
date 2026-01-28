package com.xht.modules.generate.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import com.xht.modules.common.enums.DataBaseTypeEnums;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 代码生成器-数据源配置表
 *
 * @author xht
 */
@Data
@TableName(value = "gen_data_source")
public class GenDataSourceEntity extends BasicEntity implements Serializable {

    /**
     * 数据源ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 数据源名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 数据库类型（MySQL/Oracle）
     */
    @TableField(value = "db_type")
    private DataBaseTypeEnums dbType;

    /**
     * 数据库地址
     */
    @TableField(value = "url")
    private String url;

    /**
     * 数据库用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 数据库密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 连接测试结果（success/fail）
     */
    @TableField(value = "test_result")
    private String testResult;

    /**
     * 最后测试时间
     */
    @TableField(value = "last_test_time")
    private LocalDateTime lastTestTime;

}