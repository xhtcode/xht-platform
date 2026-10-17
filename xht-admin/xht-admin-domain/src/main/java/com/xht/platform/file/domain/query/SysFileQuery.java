package com.xht.platform.file.domain.query;

import com.xht.framework.common.domain.query.PageBasicQuery;
import com.xht.platform.file.enums.FileStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 描述： 系统文件分页查询参数
 * <p>
 * 未传参的字段不参与过滤（动态条件），关键字（keyWord）模糊匹配文件原始名称与文件存储名称；
 * 排序字段由父类 {@link PageBasicQuery} 的 ascName/descName 指定，仅支持白名单字段（见 SysFileDaoImpl）
 *
 * @author xht
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统文件分页查询参数")
public class SysFileQuery extends PageBasicQuery {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件后缀（精确查询）
     */
    @Schema(description = "文件后缀（精确查询）")
    private String fileSuffix;

    /**
     * 文件存储key
     */
    @Schema(description = "文件存储key")
    private String fileStorageKey;

    /**
     * 文件类型（MIME，精确查询）
     */
    @Schema(description = "文件类型（MIME，精确查询）")
    private String fileType;

    /**
     * 文件状态
     */
    @Schema(description = "文件状态")
    private FileStatusEnum fileStatus;

    /**
     * 文件用途
     */
    @Schema(description = "文件用途")
    private String fileUsed;

    /**
     * 上传用户
     */
    @Schema(description = "上传用户")
    private String fileUploadUser;

    /**
     * 上传时间范围起（含）
     */
    @Schema(description = "上传时间范围起（含）")
    private LocalDateTime startUploadTime;

    /**
     * 上传时间范围止（含）
     */
    @Schema(description = "上传时间范围止（含）")
    private LocalDateTime endUploadTime;

}
