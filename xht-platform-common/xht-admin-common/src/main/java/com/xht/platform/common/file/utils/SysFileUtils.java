package com.xht.platform.common.file.utils;

import cn.hutool.core.io.FileUtil;
import com.xht.framework.common.constant.StringConstant;

import java.io.File;
import java.time.LocalDateTime;

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

}
