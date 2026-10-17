package com.xht.platform.common.file;

import com.xht.platform.common.file.utils.SysFileUtils;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * SysFileUtils 单元测试
 */
class SysFileUtilsTest {

    @Test
    void sanitizeFileName_shouldTakeLastPathSegment() {
        assertEquals("a.png", SysFileUtils.sanitizeFileName("../../a.png"));
        assertEquals("b.jpg", SysFileUtils.sanitizeFileName("C:\\upload\\b.jpg"));
        assertEquals("c.txt", SysFileUtils.sanitizeFileName("/tmp/dir/c.txt"));
    }

    @Test
    void sanitizeFileName_shouldReplaceIllegalChars() {
        // Windows 保留字符替换为下划线
        assertEquals("a_b_c_.txt", SysFileUtils.sanitizeFileName("a:b*c?.txt"));
        // 控制字符替换为下划线
        assertEquals("a_b.txt", SysFileUtils.sanitizeFileName("a\nb.txt"));
        // 去首尾空白
        assertEquals("spaced.txt", SysFileUtils.sanitizeFileName("  spaced.txt  "));
    }

    @Test
    void sanitizeFileName_shouldFallbackWhenBlank() {
        assertEquals("unnamed", SysFileUtils.sanitizeFileName(null));
        assertEquals("unnamed", SysFileUtils.sanitizeFileName("   "));
        assertEquals("unnamed", SysFileUtils.sanitizeFileName("///"));
    }

    @Test
    void sanitizeFileName_shouldTruncateLongName() {
        String longName = "a".repeat(300) + ".txt";
        String result = SysFileUtils.sanitizeFileName(longName);
        assertTrue(result.length() <= 200);
        assertTrue(result.endsWith(".txt"));
    }

    @Test
    void normalizeStoragePath_shouldUnifySeparator() {
        assertEquals("xht/2026/10/17", SysFileUtils.normalizeStoragePath("xht\\2026\\10\\17"));
        assertEquals("xht/a", SysFileUtils.normalizeStoragePath("/xht/a/"));
        assertEquals("", SysFileUtils.normalizeStoragePath("   "));
    }

    @Test
    void resolveThumbnailPath_shouldAppendThumbFlag() {
        assertEquals("xht/2026/a_thumb.jpg", SysFileUtils.resolveThumbnailPath("xht/2026/a.png"));
        assertEquals("xht/2026/a_thumb.jpg", SysFileUtils.resolveThumbnailPath("xht/2026/a"));
    }

    @Test
    void generateFileHash_shouldMatchKnownSha256() {
        // "hello" 的 SHA-256 为 2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824
        String expected = "2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824";
        String hash = SysFileUtils.generateFileHash(
                new ByteArrayInputStream("hello".getBytes(StandardCharsets.UTF_8)));
        assertEquals(expected, hash);
        assertEquals(64, hash.length());
    }

    @Test
    void generateFileHash_shouldReturnNullWhenStreamNull() {
        assertNull(SysFileUtils.generateFileHash(null));
    }

}
