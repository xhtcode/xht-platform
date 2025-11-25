package com.xht.generate.domain.bo;

import com.xht.generate.domain.entity.GenTableColumnEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 代码生成核心业务对象
 *
 * @author xht
 **/
@Getter
public final class GenCodeCoreBo {

    /**
     * 文件路径
     */
    @Setter
    private String filePath;

    /**
     * 文件名
     */
    @Setter
    private String fileName;

    /**
     * 表名
     */
    @Setter
    private String tableName;

    /**
     * 生成的代码内容
     */
    @Setter
    private String code;

    /**
     * 文件类型
     */
    @Setter
    private String fileType;

    /**
     * 忽略的字段 逗号分割
     */
    private final List<String> ignoreField;

    public GenCodeCoreBo(List<String> ignoreField) {
        this.ignoreField = Objects.requireNonNullElseGet(ignoreField, Collections::emptyList);
    }

    /**
     * 根据忽略字段过滤表列实体列表
     *
     * @param columnEntityList 表列实体列表
     * @return 过滤后的表列实体列表，如果execute为true则返回包含忽略字段的列，否则返回原列表
     */
    public List<GenTableColumnEntity> filter(List<GenTableColumnEntity> columnEntityList) {
        return columnEntityList.stream().filter(column -> this.ignoreField.contains(column.getCodeName())).toList();
    }

}


