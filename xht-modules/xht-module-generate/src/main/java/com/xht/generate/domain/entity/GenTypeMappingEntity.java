package com.xht.generate.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 代码生成器-数据类型映射表
 */
@TableName(value ="gen_type_mapping")
@Data
public class GenTypeMappingEntity extends BasicEntity implements Serializable {
    /**
     * 映射ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 数据库类型（MySQL/Oracle）
     */
    private String dbType;

    /**
     * 数据库数据类型（如：INT/VARCHAR2）
     */
    private String dbDataType;

    /**
     * 目标编程语言（Java/TypeScript）
     */
    private String targetLanguage;

    /**
     * 目标语言数据类型（如：Integer/number）
     */
    private String targetDataType;

    /**
     * 导入包路径（如：java.time.LocalDateTime）
     */
    private String importPackage;




}