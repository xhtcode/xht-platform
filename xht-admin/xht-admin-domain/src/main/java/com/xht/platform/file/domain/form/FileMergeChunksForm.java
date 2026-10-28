package com.xht.platform.file.domain.form;

import com.xht.framework.common.domain.form.BasicForm;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 描述： 文件合并分片表单
 *
 * @author xht
 **/
@Data
@Schema(description = "文件合并分片表单")
public class FileMergeChunksForm extends BasicForm {

    /**
     * 分片上传标识，与分片上传时保持一致
     */
    @Schema(description = "分片上传标识，与分片上传时保持一致")
    @NotEmpty(message = "分片上传标识不能为空")
    private String uploadId;

    /**
     * 原始文件名（含后缀）
     */
    @Schema(description = "原始文件名（含后缀）")
    @NotEmpty(message = "原始文件名不能为空")
    private String fileName;

    /**
     * 分片总数
     */
    @Schema(description = "分片总数")
    @NotEmpty(message = "分片总数不能为空")
    private Integer chunkTotal;

    /**
     * 文件用途标识
     */
    @Schema(description = "文件用途标识")
    private String fileUsed;

    /**
     * 文件描述
     */
    @Schema(description = "文件描述")
    private String fileDesc;

    /**
     * 文件整体MD5（可选，用于合并后完整性校验）
     */
    @Schema(description = "文件整体MD5（可选，用于合并后完整性校验）")
    private String fileHash;
}
