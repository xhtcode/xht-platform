package com.xht.platform.file.thumbnail;

import com.xht.framework.file.configurers.StorageProperties;
import com.xht.framework.file.core.FileStorageRepository;
import com.xht.framework.file.core.LocalFileStorageRepository;
import com.xht.platform.file.dao.SysFileDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * ThumbnailGeneratorImpl 单元测试（基于临时目录的真实本地存储）
 */
class ThumbnailGeneratorImplTest {

    @TempDir
    Path tempDir;

    private FileStorageRepository repository;

    private ThumbnailGeneratorImpl thumbnailGenerator;

    @BeforeEach
    void setUp() {
        StorageProperties storageProperties = new StorageProperties();
        storageProperties.getLocal().setBasePath(tempDir.toString());
        repository = new LocalFileStorageRepository(storageProperties);
        thumbnailGenerator = new ThumbnailGeneratorImpl(
                repository, Mockito.mock(SysFileDao.class), new ThumbnailProperties());
    }

    @Test
    void supports_shouldOnlyAllowImageSuffixes() {
        assertTrue(thumbnailGenerator.supports("png"));
        assertTrue(thumbnailGenerator.supports("JPG"));
        assertTrue(thumbnailGenerator.supports("webp"));
        assertFalse(thumbnailGenerator.supports("pdf"));
        assertFalse(thumbnailGenerator.supports(null));
    }

    @Test
    void generate_shouldProduceJpegWithinTargetSize() throws Exception {
        // 400x300 纯色 PNG 源图
        BufferedImage src = new BufferedImage(400, 300, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = src.createGraphics();
        graphics.setColor(Color.BLUE);
        graphics.fillRect(0, 0, 400, 300);
        graphics.dispose();
        uploadImage(src, "png", "test/src.png");

        thumbnailGenerator.generate("test/src.png", "test/src_thumb.jpg");

        byte[] thumbBytes = downloadBytes("test/src_thumb.jpg");
        // JPEG 魔术头 FF D8 FF
        assertEquals((byte) 0xFF, thumbBytes[0]);
        assertEquals((byte) 0xD8, thumbBytes[1]);
        assertEquals((byte) 0xFF, thumbBytes[2]);
        // 等比缩放且不超过 300
        BufferedImage thumbImage = ImageIO.read(new ByteArrayInputStream(thumbBytes));
        assertTrue(thumbImage.getWidth() <= 300);
        assertTrue(thumbImage.getHeight() <= 300);
        assertEquals(300, thumbImage.getWidth());
        assertEquals(225, thumbImage.getHeight());
    }

    @Test
    void generate_transparentPng_shouldFlattenToWhiteBackground() throws Exception {
        // 全透明 ARGB 图（ARGB 初始值为 0，即全透明）
        BufferedImage src = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
        uploadImage(src, "png", "test/transparent.png");

        thumbnailGenerator.generate("test/transparent.png", "test/transparent_thumb.jpg");

        BufferedImage thumbImage = ImageIO.read(new ByteArrayInputStream(downloadBytes("test/transparent_thumb.jpg")));
        // 透明区域应为白底而非黑底
        assertEquals(0xFFFFFF, thumbImage.getRGB(0, 0) & 0xFFFFFF);
    }

    @Test
    void generate_largeSource_shouldSubsampleWithoutOom() throws Exception {
        // 5000x4000 大图（超过 maxSourceDimension=4096，触发下采样路径）
        BufferedImage src = new BufferedImage(5000, 4000, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = src.createGraphics();
        graphics.setColor(Color.RED);
        graphics.fillRect(0, 0, 5000, 4000);
        graphics.dispose();
        uploadImage(src, "png", "test/large.png");

        thumbnailGenerator.generate("test/large.png", "test/large_thumb.jpg");

        BufferedImage thumbImage = ImageIO.read(new ByteArrayInputStream(downloadBytes("test/large_thumb.jpg")));
        assertTrue(thumbImage.getWidth() <= 300);
        assertTrue(thumbImage.getHeight() <= 300);
    }

    /**
     * 上传图片到临时本地存储
     */
    private void uploadImage(BufferedImage image, String format, String storagePath) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, format, byteArrayOutputStream);
        repository.upload(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()), storagePath);
    }

    /**
     * 从临时本地存储下载字节
     */
    private byte[] downloadBytes(String storagePath) throws Exception {
        try (InputStream inputStream = repository.download(storagePath)) {
            return inputStream.readAllBytes();
        }
    }

}
