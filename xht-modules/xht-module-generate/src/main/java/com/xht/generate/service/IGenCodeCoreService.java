package com.xht.generate.service;

import com.xht.generate.domain.bo.GenCodeCoreBo;
import com.xht.generate.domain.request.GenCodeCoreRequest;

import java.util.List;

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
    byte[] generateCode(GenCodeCoreRequest genCodeCoreRequest);

    /**
     * 预览代码
     *
     * @param genCodeCoreRequest 代码生成核心请求参数
     */
    List<GenCodeCoreBo> viewCode(GenCodeCoreRequest genCodeCoreRequest);
}
