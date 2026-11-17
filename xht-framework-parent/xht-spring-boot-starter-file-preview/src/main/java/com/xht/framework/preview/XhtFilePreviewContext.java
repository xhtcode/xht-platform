package com.xht.framework.preview;

import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.preview.core.bo.FilePreviewBO;
import com.xht.framework.utils.AssertUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 描述： 文件预览上下文
 *
 * @author xht
 **/
@Slf4j
@RequiredArgsConstructor
public class XhtFilePreviewContext {

    private final List<FilePreview> filePreviews;

    /**
     * 文件预览
     *
     * @param request       request
     * @param response      response
     * @param filePreviewBO 文件预览参数
     */
    public void preview(HttpServletRequest request, HttpServletResponse response, FilePreviewBO filePreviewBO) {
        for (FilePreview filePreview : filePreviews) {
            if (filePreview.supports(filePreviewBO.getPreviewType())) {
                filePreview.filePreview(request, response, filePreviewBO);
                return;
            }
        }
        AssertUtils.fail(BusinessErrorCode.FILE_TYPE_NOT_EXIST);
    }
}
