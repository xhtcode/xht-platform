package com.xht.framework.preview.autoconfigure;

import com.xht.framework.file.core.FileStorageRepository;
import com.xht.framework.preview.FilePreview;
import com.xht.framework.preview.XhtFilePreviewContext;
import com.xht.framework.preview.audio.AudioPreview;
import com.xht.framework.preview.image.ImagePreview;
import com.xht.framework.preview.json.JsonPreview;
import com.xht.framework.preview.other.OtherPreview;
import com.xht.framework.preview.pdf.PdfPreview;
import com.xht.framework.preview.text.TextPreview;
import com.xht.framework.preview.video.VideoPreview;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * 描述： 文件预览自动配置类
 * <p>统一注册各预览类型处理器与分派上下文，业务方通过注入 {@code List<FilePreview>} 或
 * {@link XhtFilePreviewContext} 即可按预览类型分派预览；
 * 各 Bean 均带 {@code @ConditionalOnMissingBean}，已有同名 Bean 时不再重复注册，便于业务侧自定义覆盖</p>
 *
 * @author xht
 **/
@Slf4j
@AutoConfiguration
public class FilePreviewAutoConfiguration {

    public FilePreviewAutoConfiguration() {
        log.debug("【xht-file-preview-spring-boot-starter】FilePreviewAutoConfiguration 自动配置");
    }

    /**
     * 文本文件预览处理器：按字节块分页返回文本内容
     *
     * @param fileStorageRepository 文件存储数据访问层
     * @return {@link TextPreview}
     */
    @Bean
    @ConditionalOnMissingBean(TextPreview.class)
    public TextPreview textPreview(FileStorageRepository fileStorageRepository) {
        return new TextPreview(fileStorageRepository);
    }

    /**
     * 图片文件预览处理器：原图直接输出到响应流
     *
     * @param fileStorageRepository 文件存储数据访问层
     * @return {@link ImagePreview}
     */
    @Bean
    @ConditionalOnMissingBean(ImagePreview.class)
    public ImagePreview imagePreview(FileStorageRepository fileStorageRepository) {
        return new ImagePreview(fileStorageRepository);
    }

    /**
     * PDF 文件预览处理器：原文件输出到响应流，支持 Range 分段加载
     *
     * @param fileStorageRepository 文件存储数据访问层
     * @return {@link PdfPreview}
     */
    @Bean
    @ConditionalOnMissingBean(PdfPreview.class)
    public PdfPreview pdfPreview(FileStorageRepository fileStorageRepository) {
        return new PdfPreview(fileStorageRepository);
    }

    /**
     * 视频文件预览处理器：原文件输出到响应流，支持 Range 拖动进度
     *
     * @param fileStorageRepository 文件存储数据访问层
     * @return {@link VideoPreview}
     */
    @Bean
    @ConditionalOnMissingBean(VideoPreview.class)
    public VideoPreview videoPreview(FileStorageRepository fileStorageRepository) {
        return new VideoPreview(fileStorageRepository);
    }

    /**
     * 音频文件预览处理器：原文件输出到响应流，支持 Range 拖动进度
     *
     * @param fileStorageRepository 文件存储数据访问层
     * @return {@link AudioPreview}
     */
    @Bean
    @ConditionalOnMissingBean(AudioPreview.class)
    public AudioPreview audioPreview(FileStorageRepository fileStorageRepository) {
        return new AudioPreview(fileStorageRepository);
    }

    /**
     * JSON 文件预览处理器：原文件按 application/json 输出
     *
     * @param fileStorageRepository 文件存储数据访问层
     * @return {@link JsonPreview}
     */
    @Bean
    @ConditionalOnMissingBean(JsonPreview.class)
    public JsonPreview jsonPreview(FileStorageRepository fileStorageRepository) {
        return new JsonPreview(fileStorageRepository);
    }

    /**
     * 其他类型文件预览处理器：不支持在线预览时返回 JSON 提示走下载
     *
     * @return {@link OtherPreview}
     */
    @Bean
    @ConditionalOnMissingBean(OtherPreview.class)
    public OtherPreview otherPreview() {
        return new OtherPreview();
    }

    /**
     * 文件预览上下文：按预览类型分派到对应处理器
     *
     * @param filePreviews 各预览类型处理器
     * @return {@link XhtFilePreviewContext}
     */
    @Bean
    @ConditionalOnMissingBean(XhtFilePreviewContext.class)
    public XhtFilePreviewContext xhtFilePreviewContext(List<FilePreview> filePreviews) {
        return new XhtFilePreviewContext(filePreviews);
    }
}
