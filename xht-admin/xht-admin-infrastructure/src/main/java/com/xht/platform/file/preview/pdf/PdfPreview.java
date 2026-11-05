package com.xht.platform.file.preview.pdf;

import com.xht.platform.file.dao.FileStorageDao;
import com.xht.platform.file.entity.SysFileEntity;
import com.xht.platform.file.enums.PreviewTypeEnum;
import com.xht.platform.file.preview.FilePreview;
import com.xht.platform.file.preview.OriginalFilePreviewWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 描述： PDF 文件预览
 * <p>原样输出 PDF 字节，由前端使用 pdf.js 等渲染；支持 Range 分段请求，便于按页加载</p>
 *
 * @author xht
 **/
@Component
@RequiredArgsConstructor
public class PdfPreview implements FilePreview {

    private final FileStorageDao fileStorageDao;

    /**
     * 是否支持文件预览
     *
     * @param previewTypeEnum 预览类型
     * @return 布尔值
     */
    @Override
    public boolean supports(PreviewTypeEnum previewTypeEnum) {
        return Objects.equals(previewTypeEnum, PreviewTypeEnum.PDF);
    }

    /**
     * PDF 文件预览
     *
     * @param request       请求
     * @param response      响应
     * @param sysFileEntity 文件实体
     */
    @Override
    public void filePreview(HttpServletRequest request, HttpServletResponse response, SysFileEntity sysFileEntity) {
        OriginalFilePreviewWriter.write(request, response, sysFileEntity, fileStorageDao);
    }

}
