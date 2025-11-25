package com.xht.generate.service;

import com.xht.generate.domain.bo.GenCodeCoreBo;
import com.xht.generate.domain.form.GenCodeCoreForm;
import com.xht.generate.domain.vo.GenCodeCoreVo;

import java.util.List;
import java.util.Map;

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
     * @param genCodeCoreForm 代码生成核心请求参数，包含生成代码所需的配置信息
     * @return byte[] 生成的代码文件字节数组
     */
    byte[] generateCode(GenCodeCoreForm genCodeCoreForm);

    /**
     * 预览代码
     * 根据请求参数预览将要生成的代码内容，返回代码预览信息列表
     *
     * @param genCodeCoreForm 代码生成核心请求参数，包含生成代码所需的配置信息
     * @return List<GenCodeCoreBo> 代码预览信息列表，包含各个代码文件的内容
     */
    List<GenCodeCoreVo> viewCode(GenCodeCoreForm genCodeCoreForm);
}
