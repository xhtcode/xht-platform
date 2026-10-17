package com.xht.platform.common.file.utils;

import com.xht.framework.common.constant.StringConstant;

import java.util.Locale;
import java.util.Optional;

/**
 * 描述： HTTP Range 请求头解析工具类
 * <p>
 * 解析 {@code Range: bytes=...} 请求头，支持单段范围（start-end / start- / -suffix）；
 * 多段范围、非法格式、越界 start 一律返回 empty，由调用方降级为 200 全量响应（不做 416）
 *
 * @author xht
 **/
public abstract class RangeHeaderParser {

    /**
     * Range 头前缀
     */
    private static final String BYTES_PREFIX = "bytes=";

    /**
     * 解析 Range 请求头
     *
     * @param rangeHeader Range 请求头值（如 "bytes=0-99"）
     * @param fileSize   文件总大小（字节）
     * @return 解析出的单段范围；无法解析/多段/越界时返回 empty
     */
    public static Optional<Range> parse(String rangeHeader, long fileSize) {
        if (rangeHeader == null || rangeHeader.isBlank() || fileSize <= 0) {
            return Optional.empty();
        }
        String header = rangeHeader.strip();
        if (!header.toLowerCase(Locale.ROOT).startsWith(BYTES_PREFIX)) {
            return Optional.empty();
        }
        String spec = header.substring(BYTES_PREFIX.length()).strip();
        // 多段范围（含逗号）不支持，降级全量
        if (spec.isEmpty() || spec.contains(StringConstant.SEPARATOR)) {
            return Optional.empty();
        }
        int dashIndex = spec.indexOf('-');
        if (dashIndex < 0) {
            return Optional.empty();
        }
        String startPart = spec.substring(0, dashIndex).strip();
        String endPart = spec.substring(dashIndex + 1).strip();
        try {
            if (startPart.isEmpty()) {
                // 后缀范围：bytes=-suffix 取末尾 suffix 字节
                if (endPart.isEmpty()) {
                    return Optional.empty();
                }
                long suffix = Long.parseLong(endPart);
                if (suffix <= 0) {
                    return Optional.empty();
                }
                long start = Math.max(0, fileSize - Math.min(suffix, fileSize));
                return Optional.of(new Range(start, fileSize - 1));
            }
            long start = Long.parseLong(startPart);
            // start 越界（大于等于文件大小）视为不可满足，降级全量
            if (start < 0 || start >= fileSize) {
                return Optional.empty();
            }
            // end 越界时收敛到最后一个字节
            long end = endPart.isEmpty() ? fileSize - 1 : Math.min(Long.parseLong(endPart), fileSize - 1);
            if (end < start) {
                return Optional.empty();
            }
            return Optional.of(new Range(start, end));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    /**
     * 单段字节范围
     *
     * @param start 起始字节（含）
     * @param end   结束字节（含）
     */
    public record Range(long start, long end) {

        /**
         * 范围长度（字节数）
         *
         * @return 长度
         */
        public long length() {
            return end - start + 1;
        }

    }

}
