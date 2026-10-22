package com.xht.platform.common.file.utils;

import cn.hutool.core.io.FileUtil;
import com.xht.framework.common.constant.StringConstant;
import com.xht.framework.utils.AssertUtils;
import com.xht.framework.utils.StringUtils;

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
     * 获取文件名
     *
     * @param originalFilename 原文件名
     * @param fileSuffix       文件后缀
     * @return 文件名
     */
    public static String getFileName(String originalFilename, String fileSuffix) {
        AssertUtils.hasText(originalFilename, "文件名不能为空");
        String name = FileUtil.getName(originalFilename);
        int lastIndex = -1;
        if (StringUtils.hasText(fileSuffix)) {
            lastIndex = name.length();
        }
        if (lastIndex > 200) {
            lastIndex = 200;
        }
        if (StringUtils.hasText(fileSuffix)) {
            lastIndex = lastIndex - fileSuffix.length() - 1;
        }
        return StringUtils.substring(name, 0, lastIndex);
    }
}
