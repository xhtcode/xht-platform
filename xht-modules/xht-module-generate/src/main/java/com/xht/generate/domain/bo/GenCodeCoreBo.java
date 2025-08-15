package com.xht.generate.domain.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * 代码生成核心业务对象
 *
 * @author xht
 **/
@Data
@AllArgsConstructor
public class GenCodeCoreBo {

    /**
     * 文件路径
     */
    private  String filePath;

    /**
     * 生成的代码内容
     */
    private  String code;
}


