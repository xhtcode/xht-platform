package com.xht.generate.domain.bo;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
    private final Set<String> ignoreField;

    public GenCodeCoreBo(Collection<String> ignoreField) {
        this.ignoreField = new HashSet<>();
        for (String item : ignoreField) {
            String camelCase = StrUtil.toCamelCase(item);
            this.ignoreField.add(StrUtil.toLowerCase(camelCase));
        }
    }

}


