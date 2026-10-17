package com.xht.platform.common.file;

import com.xht.platform.common.file.utils.RangeHeaderParser;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * RangeHeaderParser 单元测试
 */
class RangeHeaderParserTest {

    private static final long FILE_SIZE = 1000;

    @Test
    void parse_shouldHandleStartEnd() {
        Optional<RangeHeaderParser.Range> range = RangeHeaderParser.parse("bytes=0-99", FILE_SIZE);
        assertTrue(range.isPresent());
        assertEquals(0, range.get().start());
        assertEquals(99, range.get().end());
        assertEquals(100, range.get().length());
    }

    @Test
    void parse_shouldHandleOpenEnd() {
        Optional<RangeHeaderParser.Range> range = RangeHeaderParser.parse("bytes=900-", FILE_SIZE);
        assertTrue(range.isPresent());
        assertEquals(900, range.get().start());
        assertEquals(999, range.get().end());
    }

    @Test
    void parse_shouldHandleSuffix() {
        Optional<RangeHeaderParser.Range> range = RangeHeaderParser.parse("bytes=-100", FILE_SIZE);
        assertTrue(range.isPresent());
        assertEquals(900, range.get().start());
        assertEquals(999, range.get().end());
        assertEquals(100, range.get().length());
    }

    @Test
    void parse_shouldClampEndBeyondFileSize() {
        Optional<RangeHeaderParser.Range> range = RangeHeaderParser.parse("bytes=500-2000", FILE_SIZE);
        assertTrue(range.isPresent());
        assertEquals(500, range.get().start());
        assertEquals(999, range.get().end());
    }

    @Test
    void parse_shouldClampSuffixBeyondFileSize() {
        Optional<RangeHeaderParser.Range> range = RangeHeaderParser.parse("bytes=-5000", FILE_SIZE);
        assertTrue(range.isPresent());
        assertEquals(0, range.get().start());
        assertEquals(999, range.get().end());
    }

    @Test
    void parse_shouldRejectInvalidOrMultiRange() {
        // 非 bytes= 前缀
        assertFalse(RangeHeaderParser.parse("items=0-99", FILE_SIZE).isPresent());
        // 多段范围
        assertFalse(RangeHeaderParser.parse("bytes=0-99,200-299", FILE_SIZE).isPresent());
        // start 越界
        assertFalse(RangeHeaderParser.parse("bytes=1000-", FILE_SIZE).isPresent());
        // end 小于 start
        assertFalse(RangeHeaderParser.parse("bytes=500-100", FILE_SIZE).isPresent());
        // 空 spec / 无横线 / 非数字
        assertFalse(RangeHeaderParser.parse("bytes=", FILE_SIZE).isPresent());
        assertFalse(RangeHeaderParser.parse("bytes=abc", FILE_SIZE).isPresent());
        assertFalse(RangeHeaderParser.parse(null, FILE_SIZE).isPresent());
        assertFalse(RangeHeaderParser.parse("bytes=0-99", 0).isPresent());
    }

}
