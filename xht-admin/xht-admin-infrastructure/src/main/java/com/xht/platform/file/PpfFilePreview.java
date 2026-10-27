package com.xht.platform.file;

import com.xht.framework.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Set;

/**
 * 描述： 原生格式文件预览实现
 * <p>
 * 浏览器可直接内联渲染的格式（PDF / 图片 / 纯文本 / 音视频）无需转换，直接按原文件流式输出；
 * Office 等需转换的格式后续新增 {@link XhtFilePreview} 实现，由 {@link #support(String)} 路由
 *
 * @author xht
 **/
@Slf4j
@Component
public class PpfFilePreview implements XhtFilePreview {

    /**
     * 支持直接预览的文件后缀（浏览器可内联渲染）
     */
    private static final Set<String> SUPPORT_SUFFIXES = Set.of(
            "pdf",
            "jpg", "jpeg", "png", "gif", "bmp", "webp", "svg",
            "txt", "log", "csv", "json", "xml", "md",
            "mp4", "webm", "ogv", "mp3", "wav", "ogg", "m4a"
    );

    /**
     * 是否支持文件预览
     *
     * @param fileSuffix 文件后缀
     * @return 是否支持文件预览
     */
    @Override
    public boolean support(String fileSuffix) {
        if (StringUtils.isEmpty(fileSuffix)) {
            return false;
        }
        return SUPPORT_SUFFIXES.contains(fileSuffix.trim().toLowerCase(Locale.ROOT));
    }

    /**
     * 文件预览处理：原生格式无需转换，直接返回原存储路径
     *
     * @param fileStoragePath 原文件存储路径
     * @return 预览用文件存储路径
     */
    @Override
    public String filePreviewHandle(String fileStoragePath) {
        log.debug("原生格式文件预览，直接使用原文件，存储路径：{}", fileStoragePath);
        return fileStoragePath;
    }

}
