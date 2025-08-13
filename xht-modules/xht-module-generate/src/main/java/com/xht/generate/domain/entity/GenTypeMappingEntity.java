package com.xht.generate.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xht.framework.mybatis.domain.entity.BasicEntity;
import com.xht.generate.constant.enums.DataBaseTypeEnums;
import com.xht.generate.constant.enums.LanguageTypeEnums;
import lombok.Data;

import java.io.Serializable;

/**
 * 代码生成器-数据类型映射表
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
     * 目标编程语言（Java/TypeScript）
     */
    @TableField(value = "target_language")
    private LanguageTypeEnums targetLanguage;

    /**
     * 目标语言数据类型（如：Integer/number）
     */
    @TableField(value = "target_data_type")
    private String targetDataType;

    /**
     * 导入包路径（如：java.time.LocalDateTime）
     */
    @TableField(value = "import_package")
    private String importPackage;

}