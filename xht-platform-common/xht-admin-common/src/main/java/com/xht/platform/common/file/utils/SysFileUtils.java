package com.xht.platform.common.file.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.HexUtil;
import com.xht.framework.common.constant.StringConstant;
import com.xht.framework.utils.crypto.enums.DigestAlgorithmEnum;
import com.xht.platform.common.file.constant.SysFileConstant;

import java.io.File;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

/**
 * 描述： 系统文件工具类
 *
 * @author xht
 **/
public abstract class SysFileUtils {
    /**
     * 文件名截断标识
     */
    private static final String FILE_NAME_TRUNCATED_FLAG = "...";

    /**
     * 文件名最大长度
     */
    private static final int MAX_FILE_NAME_LENGTH = 200;

    /**
     * 流式计算文件哈希的缓冲区大小
     */
    private static final int HASH_BUFFER_SIZE = 8192;

    /**
     * 文件名中的非法字符（路径分隔符与 Windows 保留字符）
     */
    private static final Pattern ILLEGAL_FILENAME_CHARS = Pattern.compile("[\\\\/:*?\"<>|\\x00-\\x1f]");

    /**
     * 多级路径分隔符（用于取文件名最后一段）
     */
    private static final Pattern PATH_SEGMENT = Pattern.compile("[/\\\\]");

    /**
     * 截断超长的文件名（保留扩展名），截断后形如：前缀...扩展名
     * <p>
     * 文件名区分度通常集中在主文件名前缀与扩展名，因此保留前缀并截断后部
     *
     * @param fileName 文件名
     * @return 截断后的文件名
     */
    public static String truncateFileName(String fileName) {
        if (fileName == null || fileName.length() <= MAX_FILE_NAME_LENGTH) {
            return fileName;
        }
        String suffix = FileUtil.getSuffix(fileName);
        if (suffix.isEmpty()) {
            return fileName.substring(0, MAX_FILE_NAME_LENGTH);
        }
        // 需为截断标识、小数点、扩展名预留的长度
        int reservedLength = FILE_NAME_TRUNCATED_FLAG.length() + StringConstant.POINT.length() + suffix.length();
        int keepLength = MAX_FILE_NAME_LENGTH - reservedLength;
        if (keepLength <= 0) {
            return fileName.substring(0, MAX_FILE_NAME_LENGTH);
        }
        return fileName.substring(0, keepLength) + FILE_NAME_TRUNCATED_FLAG + StringConstant.POINT + suffix;
    }

    /**
     * 获取默认文件路径
     *
     * @return 默认文件路径
     */
    public static String generateDefaultFilePath() {
        LocalDateTime now = LocalDateTime.now();
        return StringConstant.XHT + File.separator + now.getYear() + File.separator + now.getMonthValue() + File.separator + now.getDayOfMonth();
    }

    /**
     * 清洗原始文件名，消除路径穿越与非法字符风险
     * <p>
     * 处理规则（按顺序）：null/空白 → 兜底名；仅取路径最后一段（防 ../ 穿越）；
     * 剔除控制字符；替换 Windows 保留字符与路径分隔符为下划线；去首尾空白与小数点；最后统一截断超长名称
     *
     * @param originalFilename 原始文件名
     * @return 清洗后的文件名
     */
    public static String sanitizeFileName(String originalFilename) {
        if (originalFilename == null || originalFilename.isBlank()) {
            return SysFileConstant.DEFAULT_FILE_NAME;
        }
        // 取路径最后一段，防止 "../../a.png" 之类的穿越
        String[] segments = PATH_SEGMENT.split(originalFilename);
        String fileName = segments[segments.length - 1];
        // 替换非法字符（含控制字符）
        fileName = ILLEGAL_FILENAME_CHARS.matcher(fileName).replaceAll("_");
        // 去首尾空白与小数点（避免生成隐藏文件或纯后缀名）
        fileName = fileName.strip().replaceAll("^\\.+", "").strip();
        if (fileName.isEmpty()) {
            return SysFileConstant.DEFAULT_FILE_NAME;
        }
        return truncateFileName(fileName);
    }

    /**
     * 归一化存储路径：统一使用 / 分隔符，并去除首尾分隔符
     * <p>
     * 避免 Windows 与 Linux 路径分隔符混用导致跨平台存储路径错乱
     *
     * @param storagePath 存储路径
     * @return 归一化后的存储路径
     */
    public static String normalizeStoragePath(String storagePath) {
        if (storagePath == null || storagePath.isBlank()) {
            return StringConstant.EMPTY;
        }
        return storagePath.replace('\\', '/').replaceAll("^/+", "").replaceAll("/+$", "");
    }

    /**
     * 根据原图存储路径解析缩略图存储路径：{@code {去后缀路径}_thumb.jpg}
     *
     * @param storagePath 原图存储路径
     * @return 缩略图存储路径
     */
    public static String resolveThumbnailPath(String storagePath) {
        if (storagePath == null || storagePath.isBlank()) {
            return StringConstant.EMPTY;
        }
        int dotIndex = storagePath.lastIndexOf(StringConstant.POINT);
        String base = dotIndex > 0 ? storagePath.substring(0, dotIndex) : storagePath;
        return base + SysFileConstant.THUMBNAIL_NAME_FLAG + StringConstant.POINT + "jpg";
    }

    /**
     * 流式计算文件 SHA-256 哈希（边读边算，不将文件全量读入内存）
     *
     * @param inputStream 文件输入流（方法内不关闭，由调用方管理）
     * @return SHA-256 十六进制字符串；计算失败返回 null
     */
    public static String generateFileHash(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance(DigestAlgorithmEnum.SHA256.getValue());
            try (DigestInputStream digestInputStream = new DigestInputStream(inputStream, digest)) {
                byte[] buffer = new byte[HASH_BUFFER_SIZE];
                while (digestInputStream.read(buffer) != -1) {
                    // 流经 DigestInputStream 即自动更新摘要，无需额外处理
                }
            }
            return HexUtil.encodeHexStr(digest.digest());
        } catch (Exception e) {
            return null;
        }
    }

}
