package com.xht.platform.file.preview.audio;

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
 * 描述： 音频文件预览
 * <p>原样输出音频字节，浏览器的 audio 标签依赖 Range 分段请求拖动进度，故必须支持 206 响应</p>
 *
 * @author xht
 **/
@Component
@RequiredArgsConstructor
public class AudioPreview implements FilePreview {

    private final FileStorageDao fileStorageDao;

    /**
     * 是否支持文件预览
     *
     * @param previewTypeEnum 预览类型
     * @return 布尔值
     */
    @Override
    public boolean supports(PreviewTypeEnum previewTypeEnum) {
        return Objects.equals(previewTypeEnum, PreviewTypeEnum.AUDIO);
    }

    /**
     * 音频文件预览
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
