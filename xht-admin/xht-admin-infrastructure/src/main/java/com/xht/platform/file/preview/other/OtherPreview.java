package com.xht.platform.file.preview.other;

import com.xht.framework.utils.ServletUtils;
import com.xht.platform.file.entity.SysFileEntity;
import com.xht.platform.file.enums.PreviewTypeEnum;
import com.xht.platform.file.preview.FilePreview;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 描述： 不支持预览的文件处理
 * <p>不输出文件内容，返回 JSON 告知前端该文件只能下载查看</p>
 *
 * @author xht
 **/
@Component
public class OtherPreview implements FilePreview {

    /**
     * 是否支持文件预览
     *
     * @param previewTypeEnum 预览类型
     * @return 布尔值
     */
    @Override
    public boolean supports(PreviewTypeEnum previewTypeEnum) {
        return Objects.equals(previewTypeEnum, PreviewTypeEnum.OTHER);
    }

    /**
     * 不支持预览的文件：返回下载提示
     *
     * @param request       请求
     * @param response      响应
     * @param sysFileEntity 文件实体
     */
    @Override
    public void filePreview(HttpServletRequest request, HttpServletResponse response, SysFileEntity sysFileEntity) {
        ServletUtils.writeJson(response, new OtherPreviewVO());
    }

}
