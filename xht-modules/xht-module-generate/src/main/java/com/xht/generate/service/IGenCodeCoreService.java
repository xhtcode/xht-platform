package com.xht.generate.service;

import com.xht.generate.domain.request.GenCodeCoreRequest;

/**
 * 代码生成核心服务
 *
 * @author xht
 **/
public interface IGenCodeCoreService {

    /**
     * 生成代码
     *
     * @param genCodeCoreRequest 代码生成核心请求参数
     */
    void generateCode(GenCodeCoreRequest genCodeCoreRequest);

}
