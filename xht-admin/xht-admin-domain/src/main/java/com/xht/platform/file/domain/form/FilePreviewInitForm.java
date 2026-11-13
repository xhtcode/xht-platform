package com.xht.platform.file.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 描述： 文件预览初始化请求
 *
 * @author xht
 **/
@Data
@Schema(description = "文件预览初始化请求")
public class FilePreviewInitForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件存储Key
     */
    @Schema(description = "文件存储Key，与 fileId 二选一")
    private String fileStorageKey;

}
