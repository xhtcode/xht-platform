package com.xht.framework.preview.image;

import com.xht.framework.file.core.FileStorageRepository;
import com.xht.framework.preview.FilePreview;
import com.xht.framework.preview.OriginalFilePreviewWriter;
import com.xht.framework.preview.core.bo.FilePreviewBO;
import com.xht.framework.file.enums.PreviewTypeEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

;

/**
 * 描述： 图片文件预览
 * <p>原样输出图片字节，浏览器按 Content-Type 内联渲染；不做缩略图与格式转换</p>
 *
 * @author xht
 **/
@RequiredArgsConstructor
public class ImagePreview implements FilePreview {

    private final FileStorageRepository fileStorageRepository;

    /**
     * 是否支持文件预览
     *
     * @param previewTypeEnum 预览类型
     * @return 布尔值
     */
    @Override
    public boolean supports(PreviewTypeEnum previewTypeEnum) {
        return Objects.equals(previewTypeEnum, PreviewTypeEnum.IMAGE);
    }

    /**
     * 图片文件预览
     *
     * @param request       请求
     * @param response      响应
     * @param filePreviewBO 文件实体
     */
    @Override
    public void filePreview(HttpServletRequest request, HttpServletResponse response, FilePreviewBO filePreviewBO) {
        OriginalFilePreviewWriter.write(request, response, filePreviewBO, fileStorageRepository);
    }

}
