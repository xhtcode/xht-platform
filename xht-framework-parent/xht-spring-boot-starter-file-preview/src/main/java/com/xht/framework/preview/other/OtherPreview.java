package com.xht.framework.preview.other;

import com.xht.framework.preview.FilePreview;
import com.xht.framework.preview.core.bo.FilePreviewBO;
import com.xht.framework.file.enums.PreviewTypeEnum;
import com.xht.framework.utils.ServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Objects;


/**
 * 描述： 不支持预览的文件处理
 * <p>不输出文件内容，返回 JSON 告知前端该文件只能下载查看</p>
 *
 * @author xht
 **/
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
     * @param filePreviewBO 文件实体
     */
    @Override
    public void filePreview(HttpServletRequest request, HttpServletResponse response, FilePreviewBO filePreviewBO) {
        ServletUtils.writeJson(response, new OtherPreviewVO());
    }

}
