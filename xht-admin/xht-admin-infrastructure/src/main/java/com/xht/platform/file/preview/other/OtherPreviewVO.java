package com.xht.platform.file.preview.other;

import com.xht.platform.file.enums.PreviewTypeEnum;
import lombok.Data;

/**
 * 描述： 不支持预览的文件响应
 * <p>无法在线渲染的文件（如 Office 文档、压缩包）不输出文件内容，
 * 只返回一段 JSON 告知前端改走下载</p>
 *
 * @author xht
 **/
@Data
public class OtherPreviewVO {

    /**
     * 预览类型：固定为 other
     */
    private PreviewTypeEnum previewMode = PreviewTypeEnum.OTHER;

    /**
     * 是否需要下载后查看
     */
    private boolean download = true;

    /**
     * 提示信息
     */
    private String message = "当前文件类型不支持在线预览，请下载后查看";

}
