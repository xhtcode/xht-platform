package com.xht.platform.common.file.utils;

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
     * 获取默认文件路径
     *
     * @return 默认文件路径
     */
    public static String generateDefaultFilePath() {
        LocalDateTime now = LocalDateTime.now();
        return StringConstant.XHT + File.separator + now.getYear() + File.separator + now.getMonthValue() + File.separator + now.getDayOfMonth();
    }

    /**
     * 检查文件路径是否包含 traversal characters
     *
     * @param content 文件路径
     * @return 是否包含 traversal characters
     */
    public static boolean checkTraversal(String content) {
        System.out.println(content);
        return content.contains("..") || content.contains("/");
    }
}
