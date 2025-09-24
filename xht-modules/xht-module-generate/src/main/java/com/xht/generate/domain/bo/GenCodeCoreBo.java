package com.xht.generate.domain.bo;

import cn.hutool.core.util.StrUtil;
import com.xht.framework.core.constant.StringConstant;
import com.xht.generate.domain.entity.GenTableColumnEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
     * 生成的代码内容
     */
    @Setter
    private String code;

    /**
     * 忽略的字段 逗号分割
     */
    private final List<String> ignoreField;

    public GenCodeCoreBo(List<String> ignoreField) {
        this.ignoreField = ignoreField;
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


