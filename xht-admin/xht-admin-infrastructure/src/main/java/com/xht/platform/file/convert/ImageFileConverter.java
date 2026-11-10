package com.xht.platform.file.convert;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

/**
 * 描述： 图片转换器
 * <p>support 图片转 PDF、图片转 PNG（WebP 在无原生编码器时回退 PNG）</p>
 *
 * @author xht
 **/
@Slf4j
@Component
public class ImageFileConverter implements FileConverter {

    /**
     * 支持转换的图片后缀
     */
    private static final List<String> IMAGE_SUFFIXES = List.of(
            "jpg", "jpeg", "png", "gif", "webp", "bmp", "tif", "tiff", "heic", "heif");

    /**
     * 最长边限制
     */
    private static final int MAX_EDGE = 4096;

    @Override
    public boolean supports(String sourceSuffix, String targetFormat) {
        return sourceSuffix != null
                && IMAGE_SUFFIXES.contains(sourceSuffix.toLowerCase(Locale.ROOT))
                && ("pdf".equalsIgnoreCase(targetFormat)
                || "webp".equalsIgnoreCase(targetFormat)
                || "png".equalsIgnoreCase(targetFormat));
    }

    @Override
    public void convert(Path source, String sourceSuffix, Path target, String targetFormat) throws Exception {
        BufferedImage image = ImageIO.read(source.toFile());
        if (image == null) {
            throw new IOException("无法解析图片：" + sourceSuffix);
        }
        image = scaleIfNeeded(image);
        Files.createDirectories(target.getParent());
        if ("pdf".equalsIgnoreCase(targetFormat)) {
            writePdf(image, target);
        } else {
            // 无原生 WebP 编码器时回退 PNG，目标路径已由执行器按 png 命名
            if (!ImageIO.write(image, "png", target.toFile())) {
                throw new IOException("图片编码失败");
            }
        }
    }

    /**
     * 按最长边缩放
     *
     * @param image 原图
     * @return 缩放后的图片
     * @throws IOException 缩放异常
     */
    private BufferedImage scaleIfNeeded(BufferedImage image) throws IOException {
        int width = image.getWidth();
        int height = image.getHeight();
        if (width <= MAX_EDGE && height <= MAX_EDGE) {
            return image;
        }
        return Thumbnails.of(image).size(MAX_EDGE, MAX_EDGE).asBufferedImage();
    }

    /**
     * 写入单页 PDF
     *
     * @param image  图片
     * @param target 目标路径
     * @throws IOException 写入异常
     */
    private void writePdf(BufferedImage image, Path target) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(new PDRectangle(image.getWidth(), image.getHeight()));
            document.addPage(page);
            PDImageXObject pdfImage = LosslessFactory.createFromImage(document, image);
            try (PDPageContentStream content = new PDPageContentStream(document, page)) {
                content.drawImage(pdfImage, 0, 0, image.getWidth(), image.getHeight());
            }
            document.save(target.toFile());
        }
    }

}
