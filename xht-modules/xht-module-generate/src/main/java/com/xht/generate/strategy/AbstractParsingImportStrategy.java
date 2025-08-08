package com.xht.generate.strategy;

import com.xht.generate.constant.ParsingTypeEnums;

/**
 * 解析导入策略
 *
 * @author xht
 **/
public abstract class AbstractParsingImportStrategy {

    /**
     * 支持的解析类型
     * @return 解析类型枚举 {@link ParsingTypeEnums}
     */
    protected abstract ParsingTypeEnums support();

}
