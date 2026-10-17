package com.xht.platform.file.thumbnail;

import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.file.core.FileStorageRepository;
import com.xht.platform.common.file.constant.SysFileConstant;
import com.xht.platform.file.dao.SysFileDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

/**
 * 描述： 缩略图生成器实现
 * <p>
 * 核心实现说明：
 * <ul>
 *     <li>大图通过 {@link ImageReadParam#setSourceSubsampling} 抽样读取，避免解码期 OOM</li>
 *     <li>缩放目标画布使用 TYPE_INT_RGB 并填充白底，避免透明 PNG 转 JPEG 出现黑底</li>
 *     <li>JPEG 编码显式指定压缩质量</li>
 *     <li>全程仅 Graphics2D.drawImage 缩放，不绘制文字，无字体依赖，无头环境（Linux 容器）安全</li>
 *     <li>异步线程池名称来自 {@code ThreadPollConfig.ASYNC_EXECUTOR_THREAD_NAME}，异步线程内不读取 SecurityContext</li>
 * </ul>
 *
 * @author xht
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class ThumbnailGeneratorImpl implements ThumbnailGenerator {

    /**
     * 全局异步线程池 bean 名称（见 start 模块 ThreadPollConfig）
     */
    private static final String ASYNC_EXECUTOR_NAME = "xht-async-executor";

    /**
     * 支持生成缩略图的文件后缀
     */
    private static final Set<String> SUPPORTED_SUFFIXES = Set.of("jpg", "jpeg", "png", "gif", "bmp", "webp");

    /**
     * 缩略图输出格式
     */
    private static final String OUTPUT_FORMAT = "jpg";

    private final FileStorageRepository fileStorageRepository;

    private final SysFileDao sysFileDao;

    private final ThumbnailProperties thumbnailProperties;

    @Override
    public boolean supports(String fileSuffix) {
        if (fileSuffix == null || fileSuffix.isBlank()) {
            return false;
        }
        return SUPPORTED_SUFFIXES.contains(fileSuffix.strip().toLowerCase(Locale.ROOT));
    }

    @Override
    public void generate(String sourceStoragePath, String targetStoragePath) {
        try (InputStream inputStream = fileStorageRepository.download(sourceStoragePath)) {
            byte[] thumbnailBytes = scaleToBytes(inputStream);
            if (thumbnailBytes == null || thumbnailBytes.length == 0) {
                log.warn("缩略图生成结果为空，sourceStoragePath：{}", sourceStoragePath);
                return;
            }
            fileStorageRepository.upload(new ByteArrayInputStream(thumbnailBytes), targetStoragePath);
            log.debug("缩略图生成成功，sourceStoragePath：{}，targetStoragePath：{}", sourceStoragePath, targetStoragePath);
        } catch (Exception e) {
            log.error("缩略图生成失败，sourceStoragePath：{}", sourceStoragePath, e);
            throw new BusinessException(BusinessErrorCode.FILE_THUMBNAIL_GENERATE_FAIL, e.getMessage(), e);
        }
    }

    @Async(ASYNC_EXECUTOR_NAME)
    @Override
    public void generateAsync(Long fileId, String sourceStoragePath, String targetStoragePath) {
        try {
            generate(sourceStoragePath, targetStoragePath);
            // 异步回写缩略图信息；fileStorageKey 由调用方保证与 sourceStoragePath 对应
            sysFileDao.updateThumbnailById(fileId, extractThumbKey(sourceStoragePath), targetStoragePath);
        } catch (Exception e) {
            // 异步任务失败仅记录日志，不向上抛出（线程内异常无法传播到调用方）
            log.error("异步生成缩略图失败，fileId：{}，sourceStoragePath：{}", fileId, sourceStoragePath, e);
        }
    }

    /**
     * 缩放并编码为 JPEG 字节数组
     *
     * @param inputStream 原图输入流（方法内关闭）
     * @return 缩略图字节数组；读取/解码失败返回 null
     */
    private byte[] scaleToBytes(InputStream inputStream) {
        try (ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream)) {
            Iterator<ImageReader> readers = ImageIO.getImageReaders(imageInputStream);
            if (!readers.hasNext()) {
                log.warn("无法识别的图片格式，跳过缩略图生成");
                return null;
            }
            ImageReader reader = readers.next();
            try {
                reader.setInput(imageInputStream);
                int srcWidth = reader.getWidth(0);
                int srcHeight = reader.getHeight(0);
                // 大图按倍数抽样读取，降低解码内存峰值
                int subsampling = 1;
                int maxEdge = Math.max(srcWidth, srcHeight);
                while (maxEdge / subsampling > thumbnailProperties.getMaxSourceDimension()) {
                    subsampling *= 2;
                }
                ImageReadParam readParam = reader.getDefaultReadParam();
                if (subsampling > 1) {
                    readParam.setSourceSubsampling(subsamplingFactor(subsampling), subsamplingFactor(subsampling), 0, 0);
                }
                BufferedImage srcImage = reader.read(0, readParam);
                return encodeThumbnail(srcImage);
            } finally {
                reader.dispose();
            }
        } catch (Exception e) {
            log.error("缩放图片失败", e);
            return null;
        }
    }

    /**
     * 将源图等比缩放（不超过目标宽高）并编码为带压缩质量的 JPEG
     *
     * @param srcImage 源图
     * @return JPEG 字节数组
     */
    private byte[] encodeThumbnail(BufferedImage srcImage) throws Exception {
        int srcWidth = srcImage.getWidth();
        int srcHeight = srcImage.getHeight();
        // 等比缩放，且不放大小图
        double scale = Math.min((double) thumbnailProperties.getWidth() / srcWidth,
                (double) thumbnailProperties.getHeight() / srcHeight);
        scale = Math.min(scale, 1.0d);
        int targetWidth = Math.max(1, (int) Math.round(srcWidth * scale));
        int targetHeight = Math.max(1, (int) Math.round(srcHeight * scale));
        // TYPE_INT_RGB 画布 + 白底：避免透明 PNG 转 JPEG 出现黑底
        BufferedImage targetImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = targetImage.createGraphics();
        try {
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, targetWidth, targetHeight);
            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            graphics.drawImage(srcImage, 0, 0, targetWidth, targetHeight, null);
        } finally {
            graphics.dispose();
        }
        return encodeJpeg(targetImage);
    }

    /**
     * JPEG 编码（显式压缩质量）
     *
     * @param image 图片
     * @return JPEG 字节数组
     */
    private byte[] encodeJpeg(BufferedImage image) throws Exception {
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(OUTPUT_FORMAT);
        if (!writers.hasNext()) {
            throw new BusinessException(BusinessErrorCode.FILE_THUMBNAIL_GENERATE_FAIL);
        }
        ImageWriter writer = writers.next();
        ImageWriteParam writeParam = writer.getDefaultWriteParam();
        writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        writeParam.setCompressionQuality(thumbnailProperties.getQuality());
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(byteArrayOutputStream)) {
            writer.setOutput(imageOutputStream);
            writer.write(null, new IIOImage(image, null, null), writeParam);
            return byteArrayOutputStream.toByteArray();
        } finally {
            writer.dispose();
        }
    }

    /**
     * 下采样参数修正（防止为 0）
     *
     * @param subsampling 计算得到的抽样倍数
     * @return 修正后的抽样倍数（>=1）
     */
    private int subsamplingFactor(int subsampling) {
        return Math.max(1, subsampling);
    }

    /**
     * 从原图存储路径推导缩略图存储 key：{文件名去后缀}_thumb
     *
     * @param sourceStoragePath 原图存储路径
     * @return 缩略图存储 key
     */
    private String extractThumbKey(String sourceStoragePath) {
        int slashIndex = sourceStoragePath.lastIndexOf('/');
        String fileName = slashIndex >= 0 ? sourceStoragePath.substring(slashIndex + 1) : sourceStoragePath;
        int dotIndex = fileName.lastIndexOf('.');
        String base = dotIndex > 0 ? fileName.substring(0, dotIndex) : fileName;
        return base + SysFileConstant.THUMBNAIL_NAME_FLAG;
    }

}
