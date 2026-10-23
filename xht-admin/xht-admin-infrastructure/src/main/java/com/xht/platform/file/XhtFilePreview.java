package com.xht.platform.file;

import com.xht.platform.file.domain.response.SysFileResponse;

/**
 * 描述： 文件预览接口
 *
 * @author xht
 **/
public interface XhtFilePreview {

    /**
     * 文件预览处理
     *
     * @param sysFileResponse 文件信息
     */
    String filePreviewHandle(SysFileResponse sysFileResponse);

}
