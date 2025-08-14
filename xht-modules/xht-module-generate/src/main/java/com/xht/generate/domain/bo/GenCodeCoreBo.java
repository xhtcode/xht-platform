package com.xht.generate.domain.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 代码生成核心业务对象
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public class GenCodeCoreBo {

    /**
     * 文件路径
     */
    private final String filePath;

    /**
     * 生成的代码内容
     */
    private final String code;
}


