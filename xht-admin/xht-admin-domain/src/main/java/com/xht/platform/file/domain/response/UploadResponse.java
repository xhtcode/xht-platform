package com.xht.platform.file.domain.response;

import com.xht.framework.common.domain.response.BasicResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 描述：文件上传响应实体
 *
 * @author xht
 **/
@Data
@Schema(description = "文件上传响应实体")
@AllArgsConstructor
public class UploadResponse extends BasicResponse {

    /**
     * 文件上传后的存储key
     */
    @Schema(description = "文件上传后的存储key")
    private String fileStorageKey;

    /**
     * 文件上传后的访问地址
     */
    @Schema(description = "文件上传后的访问地址")
    private String filePreviewUrl;

}
