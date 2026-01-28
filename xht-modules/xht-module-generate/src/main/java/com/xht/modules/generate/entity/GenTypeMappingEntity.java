package com.xht.modules.generate.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import com.xht.modules.common.enums.DataBaseTypeEnums;
import lombok.Data;

import java.io.Serializable;

/**
 * 代码生成器-数据类型映射表
 *
 * @author xht
 */
@Data
@TableName(value = "gen_type_mapping")
public class GenTypeMappingEntity extends BasicEntity implements Serializable {

    /**
     * 映射ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 数据库类型（MySQL/Oracle）
     */
    @TableField(value = "db_type")
    private DataBaseTypeEnums dbType;

    /**
     * 数据库数据类型（如：INT/VARCHAR2）
     */
    @TableField(value = "db_data_type")
    private String dbDataType;

    /**
     * java  类型
     */
    @TableField(value = "java_type")
    private String javaType;

    /**
     * java 包类型
     */
    @TableField(value = "import_package")
    private String importPackage;

    /**
     * ts 类型
     */
    @TableField(value = "ts_type")
    private String tsType;

}