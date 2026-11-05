package com.xht.platform.file.preview.json;

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
 * 描述： JSON 文件预览
 * <p>JSON 虽然是文本，但前端需要拿到原始内容做格式化、折叠等渲染，
 * 因此不做文本分页，原样输出 application/json 字节</p>
 *
 * @author xht
 **/
@Component
@RequiredArgsConstructor
public class JsonPreview implements FilePreview {

    private final FileStorageDao fileStorageDao;

    /**
     * 是否支持文件预览
     *
     * @param previewTypeEnum 预览类型
     * @return 布尔值
     */
    @Override
    public boolean supports(PreviewTypeEnum previewTypeEnum) {
        return Objects.equals(previewTypeEnum, PreviewTypeEnum.JSON);
    }

    /**
     * JSON 文件预览
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
