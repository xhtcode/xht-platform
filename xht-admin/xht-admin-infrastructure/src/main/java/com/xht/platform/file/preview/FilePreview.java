package com.xht.platform.file.preview;

import com.xht.platform.file.entity.SysFileEntity;
import com.xht.platform.file.enums.PreviewTypeEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 描述： 文件预览
 *
 * @author xht
 **/
public interface FilePreview {

    /**
     * 是否支持文件预览
     *
     * @param previewTypeEnum 预览类型
     * @return 布尔值
     */
    boolean supports(PreviewTypeEnum previewTypeEnum);

    /**
     * 文件预览
     *
     * @param request       请求
     * @param response      响应
     * @param sysFileEntity 文件实体
     */
    void filePreview(HttpServletRequest request, HttpServletResponse response, SysFileEntity sysFileEntity);

}
