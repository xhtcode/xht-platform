package com.xht.framework.core.file;

import cn.hutool.core.util.IdUtil;
import com.xht.framework.core.utils.file.FileUtils;

/**
 * 描述 ：文件类型
 *
 * @author xht
 **/
@SuppressWarnings("unused")
public record FileType(String type, String contentType, String desc) {

    /**
     * @return 生成的文件名称
     */
    public String generateName() {
        return generateName(IdUtil.objectId());
    }

    /**
     * @param name 文件名称前缀
     * @return 生成的文件名称
     */
    public String generateName(String name) {
        return FileUtils.generateFileName(name, this.type);
    }

    /**
     * @return 生成的文件后缀
     */
    public String getFileSuffix() {
        return this.type;
    }

}
