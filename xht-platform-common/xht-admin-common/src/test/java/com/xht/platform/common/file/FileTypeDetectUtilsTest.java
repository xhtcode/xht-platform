package com.xht.platform.common.file;

import com.xht.platform.common.file.utils.FileTypeDetectUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * FileTypeDetectUtils 单元测试
 */
class FileTypeDetectUtilsTest {

    @Test
    void detectMimeType_shouldRecognizeMagicBytes() {
        // JPEG：FF D8 FF
        assertEquals("image/jpeg", FileTypeDetectUtils.detectMimeType(new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xE0, 0, 0}));
        // PNG
        assertEquals("image/png", FileTypeDetectUtils.detectMimeType(new byte[]{(byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A}));
        // GIF
        assertEquals("image/gif", FileTypeDetectUtils.detectMimeType("GIF89a....".getBytes()));
        // BMP
        assertEquals("image/bmp", FileTypeDetectUtils.detectMimeType("BM......".getBytes()));
        // PDF
        assertEquals("application/pdf", FileTypeDetectUtils.detectMimeType("%PDF-1.4".getBytes()));
        // ZIP 族
        assertEquals("application/zip", FileTypeDetectUtils.detectMimeType(new byte[]{0x50, 0x4B, 0x03, 0x04, 0, 0}));
        // MP4：偏移 4 为 ftyp
        assertEquals("video/mp4", FileTypeDetectUtils.detectMimeType(new byte[]{0, 0, 0, 0x18, 'f', 't', 'y', 'p'}));
    }

    @Test
    void detectMimeType_shouldRecognizeWebp() {
        byte[] webp = new byte[12];
        webp[0] = 'R';
        webp[1] = 'I';
        webp[2] = 'F';
        webp[3] = 'F';
        webp[8] = 'W';
        webp[9] = 'E';
        webp[10] = 'B';
        webp[11] = 'P';
        assertEquals("image/webp", FileTypeDetectUtils.detectMimeType(webp));
    }

    @Test
    void detectMimeType_shouldReturnNullForUnknown() {
        assertNull(FileTypeDetectUtils.detectMimeType("plain text content".getBytes()));
        assertNull(FileTypeDetectUtils.detectMimeType(new byte[0]));
        assertNull(FileTypeDetectUtils.detectMimeType((byte[]) null));
    }

    @Test
    void resolveMimeType_shouldInferFromSuffix() {
        assertEquals("image/png", FileTypeDetectUtils.resolveMimeType("png"));
        assertEquals("application/pdf", FileTypeDetectUtils.resolveMimeType("pdf"));
        // 无法推断时返回 null（octet-stream 视为无法推断）
        assertNull(FileTypeDetectUtils.resolveMimeType("xyz"));
        assertNull(FileTypeDetectUtils.resolveMimeType(""));
        assertNull(FileTypeDetectUtils.resolveMimeType(null));
    }

    @Test
    void isUnambiguousMime_shouldExcludeZipFamily() {
        assertTrue(FileTypeDetectUtils.isUnambiguousMime("image/jpeg"));
        assertTrue(FileTypeDetectUtils.isUnambiguousMime("image/PNG"));
        assertTrue(FileTypeDetectUtils.isUnambiguousMime("application/pdf"));
        // ZIP 族同魔术字节，不做强校验
        assertFalse(FileTypeDetectUtils.isUnambiguousMime("application/zip"));
        assertFalse(FileTypeDetectUtils.isUnambiguousMime("application/octet-stream"));
        assertFalse(FileTypeDetectUtils.isUnambiguousMime(null));
    }

}
