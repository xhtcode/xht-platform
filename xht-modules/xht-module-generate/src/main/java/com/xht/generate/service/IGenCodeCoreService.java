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
     * 根据请求参数生成对应的代码文件，并返回字节数组格式的代码内容
     *
     * @param genCodeCoreRequest 代码生成核心请求参数，包含生成代码所需的配置信息
     * @return byte[] 生成的代码文件字节数组
     */
    byte[] generateCode(GenCodeCoreRequest genCodeCoreRequest);

    /**
     * 预览代码
     * 根据请求参数预览将要生成的代码内容，返回代码预览信息列表
     *
     * @param genCodeCoreRequest 代码生成核心请求参数，包含生成代码所需的配置信息
     * @return List<GenCodeCoreBo> 代码预览信息列表，包含各个代码文件的内容
     */
    List<GenCodeCoreBo> viewCode(GenCodeCoreRequest genCodeCoreRequest);
}
