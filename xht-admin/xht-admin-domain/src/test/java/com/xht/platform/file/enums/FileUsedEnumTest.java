package com.xht.platform.file.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * FileUsedEnum 单元测试
 */
class FileUsedEnumTest {

    @Test
    void normalize_shouldFallbackToTemp() {
        assertEquals("temp", FileUsedEnum.normalize(null));
        assertEquals("temp", FileUsedEnum.normalize(""));
        assertEquals("temp", FileUsedEnum.normalize("   "));
    }

    @Test
    void normalize_shouldNormalizeCase() {
        assertEquals("avatar", FileUsedEnum.normalize("AVATAR"));
        assertEquals("avatar", FileUsedEnum.normalize(" Avatar "));
        assertEquals("contract", FileUsedEnum.normalize("contract"));
    }

    @Test
    void normalize_shouldReturnNullForIllegalValue() {
        assertNull(FileUsedEnum.normalize("unknown"));
        assertNull(FileUsedEnum.normalize("sys:user"));
    }

    @Test
    void isSupported_shouldMatchEnumValues() {
        assertTrue(FileUsedEnum.isSupported("avatar"));
        assertTrue(FileUsedEnum.isSupported("NOTICE"));
        assertFalse(FileUsedEnum.isSupported("unknown"));
        assertFalse(FileUsedEnum.isSupported(null));
    }

}
