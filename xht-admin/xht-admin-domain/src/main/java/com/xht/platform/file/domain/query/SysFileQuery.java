package com.xht.platform.file.domain.query;

import com.xht.framework.common.domain.query.PageBasicQuery;
import com.xht.platform.file.enums.FileStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 描述： 系统文件分页查询参数
 * <p>
 * 未传参的字段不参与过滤（动态条件），关键字（keyWord）模糊匹配文件原始名称与文件存储名称；
 * 排序字段由父类 {@link PageBasicQuery} 的 ascName/descName 指定
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
     * 文件原始名称（模糊查询）
     */
    @Schema(name = "fileOriginalName", description = "文件原始名称（模糊查询）")
    private String fileOriginalName;

    /**
     * 文件hash（精确查询）
     */
    @Schema(name = "fileHash", description = "文件hash（精确查询）")
    private String fileHash;

    /**
     * 文件类型（精确查询）
     */
    @Schema(name = "fileType", description = "文件类型（精确查询）")
    private String fileType;

    /**
     * 文件后缀（精确查询）
     */
    @Schema(name = "fileSuffix", description = "文件后缀（精确查询）")
    private String fileSuffix;

    /**
     * 文件状态（精确查询）
     */
    @Schema(name = "fileStatus", description = "文件状态（精确查询）")
    private FileStatusEnum fileStatus;

    /**
     * 文件用途（精确查询）
     */
    @Schema(name = "fileUsed", description = "文件用途（精确查询）")
    private String fileUsed;

    /**
     * 文件存储类型（精确查询）
     */
    @Schema(name = "fileStorageType", description = "文件存储类型（精确查询）")
    private String fileStorageType;

    /**
     * 文件上传用户（精确查询）
     */
    @Schema(name = "fileUploadUser", description = "文件上传用户（精确查询）")
    private String fileUploadUser;

}
