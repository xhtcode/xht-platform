package com.xht.platform.file.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 描述： 服务端水印服务
 * <p>对 PDF 与图片按访问主体烧录水印；不修改共享转换产物，只输出带水印副本</p>
 *
 * @author xht
 **/
@Slf4j
@Service
public class WatermarkService {

    /**
     * 中文字体路径，为空或不存在时回退 Helvetica 并过滤非 ASCII 字符
     */
    @Value("${xht.file.watermark.font-path:}")
    private String fontPath;

    /**
     * 给 PDF 烧录水印
     *
     * @param source PDF 输入流
     * @param text   水印文案
     * @return 带水印 PDF 字节
     * @throws Exception 处理异常
     */
    public synchronized byte[] watermarkPdf(InputStream source, String text) throws Exception {
        try (PDDocument document = PDDocument.load(source);
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            PDFont font = resolveFont(document);
            String safeText = font instanceof PDType0Font ? text : asciiOnly(text);
            for (PDPage page : document.getPages()) {
                try (PDPageContentStream content = new PDPageContentStream(
                        document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                    PDExtendedGraphicsState state = new PDExtendedGraphicsState();
                    state.setNonStrokingAlphaConstant(0.12f);
                    content.setGraphicsStateParameters(state);
                    content.setFont(font, 22);
                    content.setNonStrokingColor(new Color(120, 120, 120));
                    float width = page.getMediaBox().getWidth();
                    float height = page.getMediaBox().getHeight();
                    drawText(content, safeText, 40, 40, 30);
                    drawText(content, safeText, width / 2 - 120, height / 2, 30);
                    drawText(content, safeText, width - 260, height - 100, 30);
                }
            }
            document.save(output);
            return output.toByteArray();
        }
    }

    /**
     * 给图片烧录水印
     *
     * @param source 图片输入流
     * @param text   水印文案
     * @return 带水印 PNG 字节
     * @throws Exception 处理异常
     */
    public byte[] watermarkImage(InputStream source, String text) throws Exception {
        BufferedImage image = ImageIO.read(source);
        if (image == null) {
            throw new IllegalArgumentException("无法解析图片");
        }
        BufferedImage target = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = target.createGraphics();
        try {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.drawImage(image, 0, 0, null);
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.14f));
            graphics.setColor(new Color(90, 90, 90));
            graphics.setFont(new Font("SansSerif", Font.BOLD, Math.max(18, image.getWidth() / 30)));
            graphics.rotate(Math.toRadians(-30), image.getWidth() / 2.0, image.getHeight() / 2.0);
            int stepX = Math.max(260, image.getWidth() / 2);
            int stepY = Math.max(160, image.getHeight() / 3);
            for (int y = -image.getHeight(); y < image.getHeight() * 2; y += stepY) {
                for (int x = -image.getWidth(); x < image.getWidth() * 2; x += stepX) {
                    graphics.drawString(text, x, y);
                }
            }
        } finally {
            graphics.dispose();
        }
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            ImageIO.write(target, "png", output);
            return output.toByteArray();
        }
    }

    /**
     * 解析字体
     *
     * @param document PDF 文档
     * @return 字体
     */
    private PDFont resolveFont(PDDocument document) {
        if (fontPath != null && !fontPath.isBlank()) {
            Path path = Path.of(fontPath);
            if (Files.exists(path)) {
                try {
                    return PDType0Font.load(document, new File(fontPath));
                } catch (Exception e) {
                    log.warn("加载水印字体失败：{}，回退 Helvetica", fontPath, e);
                }
            }
        }
        return PDType1Font.HELVETICA;
    }

    /**
     * 绘制旋转文字
     */
    private void drawText(PDPageContentStream content, String text, float x, float y, float angle) throws Exception {
        double radians = Math.toRadians(angle);
        float cos = (float) Math.cos(radians);
        float sin = (float) Math.sin(radians);
        content.beginText();
        content.setTextMatrix(cos, sin, -sin, cos, x, y);
        content.showText(text);
        content.endText();
    }

    /**
     * 过滤非 ASCII 字符，避免 Helvetica 无法编码
     */
    private String asciiOnly(String text) {
        if (text == null) {
            return "";
        }
        return text.replaceAll("[^\\x20-\\x7E]", "?");
    }

}
