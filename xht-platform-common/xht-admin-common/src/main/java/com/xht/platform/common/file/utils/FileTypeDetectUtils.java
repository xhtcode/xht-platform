package com.xht.platform.common.file.utils;

import com.xht.framework.file.enums.MimeTypeEnums;
import com.xht.framework.utils.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Locale;

/**
 * 描述： 文件内容类型探测工具类
 * <p>
 * 通过文件头魔术字节识别真实内容类型，避免信任客户端声明的 Content-Type 与文件后缀；
 * 探测失败时回退按后缀推断，均失败时返回 null 由调用方兜底
 *
 * @author xht
 **/
public abstract class FileTypeDetectUtils {

    /**
     * 探测文件头的最大读取字节数
     */
    private static final int HEAD_BUFFER_SIZE = 4096;

    /**
     * JPEG 魔术头：FF D8 FF
     */
    private static final byte[] MAGIC_JPEG = {(byte) 0xFF, (byte) 0xD8, (byte) 0xFF};

    /**
     * PNG 魔术头：89 50 4E 47 0D 0A 1A 0A
     */
    private static final byte[] MAGIC_PNG = {(byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A};

    /**
     * PDF 魔术头：%PDF
     */
    private static final byte[] MAGIC_PDF = {0x25, 0x50, 0x44, 0x46};

    /**
     * ZIP 族魔术头：PK 03 04（zip/docx/xlsx/pptx 等同族）
     */
    private static final byte[] MAGIC_ZIP = {0x50, 0x4B, 0x03, 0x04};

    /**
     * 无歧义、可与后缀交叉校验的 MIME 类型（ZIP 族同魔术字节，不参与强校验）
     */
    private static final String[] UNAMBIGUOUS_MIME_TYPES = {
            "image/jpeg", "image/png", "image/gif", "image/bmp", "image/webp",
            "application/pdf", "video/mp4"
    };

    /**
     * 读取输入流头部字节（最多 {@link #HEAD_BUFFER_SIZE} 字节，流不会被关闭）
     *
     * @param inputStream 文件输入流
     * @return 头部字节数组
     */
    public static byte[] readHeadBytes(InputStream inputStream) {
        if (inputStream == null) {
            return new byte[0];
        }
        try {
            byte[] buffer = new byte[HEAD_BUFFER_SIZE];
            int read = inputStream.readNBytes(buffer, 0, HEAD_BUFFER_SIZE);
            return read <= 0 ? new byte[0] : Arrays.copyOf(buffer, read);
        } catch (IOException e) {
            return new byte[0];
        }
    }

    /**
     * 根据文件头魔术字节探测内容类型
     *
     * @param head 文件头字节
     * @return 探测到的 MIME 类型，无法识别返回 null
     */
    public static String detectMimeType(byte[] head) {
        if (head == null || head.length < 4) {
            return null;
        }
        // JPEG：FF D8 FF
        if (startsWith(head, MAGIC_JPEG)) {
            return "image/jpeg";
        }
        // PNG：89 50 4E 47 0D 0A 1A 0A
        if (startsWith(head, MAGIC_PNG)) {
            return "image/png";
        }
        // GIF：GIF87a / GIF89a
        if (head[0] == 'G' && head[1] == 'I' && head[2] == 'F' && head[3] == '8') {
            return "image/gif";
        }
        // BMP：BM
        if (head[0] == 'B' && head[1] == 'M') {
            return "image/bmp";
        }
        // WebP：RIFF .... WEBP
        if (head.length >= 12 && head[0] == 'R' && head[1] == 'I' && head[2] == 'F' && head[3] == 'F'
                && head[8] == 'W' && head[9] == 'E' && head[10] == 'B' && head[11] == 'P') {
            return "image/webp";
        }
        // PDF：%PDF
        if (startsWith(head, MAGIC_PDF)) {
            return "application/pdf";
        }
        // ZIP 族：PK 03 04
        if (startsWith(head, MAGIC_ZIP)) {
            return "application/zip";
        }
        // MP4：偏移 4 字节处为 ftyp
        if (head.length >= 8 && head[4] == 'f' && head[5] == 't' && head[6] == 'y' && head[7] == 'p') {
            return "video/mp4";
        }
        return null;
    }

    /**
     * 根据文件后缀推断内容类型（探测失败时的回退）
     *
     * @param fileSuffix 文件后缀（不含点）
     * @return 推断的 MIME 类型，无法推断返回 null
     */
    public static String resolveMimeType(String fileSuffix) {
        if (!StringUtils.hasText(fileSuffix)) {
            return null;
        }
        String contentType = MimeTypeEnums.getByFileSuffix(fileSuffix).getContentType();
        // DEFAULT_ 的值为 application/octet-stream，视为无法推断
        return MimeTypeEnums.DEFAULT_.getContentType().equals(contentType) ? null : contentType;
    }

    /**
     * 判断 MIME 类型是否为无歧义类型（探测结果可信，可与后缀交叉校验）
     * <p>
     * ZIP 族（docx/xlsx/zip 等）魔术字节相同，不做强校验
     *
     * @param mimeType MIME 类型
     * @return true：无歧义，可交叉校验
     */
    public static boolean isUnambiguousMime(String mimeType) {
        if (!StringUtils.hasText(mimeType)) {
            return false;
        }
        String normalized = mimeType.split(";")[0].trim().toLowerCase(Locale.ROOT);
        return Arrays.asList(UNAMBIGUOUS_MIME_TYPES).contains(normalized);
    }

    /**
     * 从输入流读取头部并探测内容类型
     *
     * @param inputStream 文件输入流
     * @return 探测到的 MIME 类型，无法识别返回 null
     */
    public static String detectMimeType(InputStream inputStream) {
        return detectMimeType(readHeadBytes(inputStream));
    }

    /**
     * 判断字节数组是否以指定前缀开头
     *
     * @param head   字节数组
     * @param prefix 前缀
     * @return 是否匹配
     */
    private static boolean startsWith(byte[] head, byte[] prefix) {
        if (head.length < prefix.length) {
            return false;
        }
        for (int i = 0; i < prefix.length; i++) {
            if (head[i] != prefix[i]) {
                return false;
            }
        }
        return true;
    }

}
