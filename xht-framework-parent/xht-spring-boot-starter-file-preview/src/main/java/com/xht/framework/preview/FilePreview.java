package com.xht.framework.preview;

import com.xht.framework.preview.core.bo.FilePreviewBO;
import com.xht.framework.file.enums.PreviewTypeEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 文件预览处理器接口
 * 不同类型文件预览实现统一实现该接口，提供能力判断与预览输出
 *
 * @author xht
 */
public interface FilePreview {

    /**
     * 判断当前处理器是否支持该预览类型
     *
     * @param previewTypeEnum 文件预览类型枚举
     * @return true-支持；false-不支持
     */
    boolean supports(PreviewTypeEnum previewTypeEnum);

    /**
     * 执行文件预览，直接输出内容到response响应流
     *
     * @param request       Http请求对象
     * @param response      Http响应对象，预览内容直接写入该响应
     * @param filePreviewBO 文件预览业务参数对象
     */
    void filePreview(HttpServletRequest request, HttpServletResponse response, FilePreviewBO filePreviewBO);
}
