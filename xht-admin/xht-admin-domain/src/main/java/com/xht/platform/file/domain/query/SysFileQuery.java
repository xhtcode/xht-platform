package com.xht.platform.file.domain.query;

import com.xht.framework.common.domain.query.PageBasicQuery;
import com.xht.framework.file.enums.FileStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * 描述： 系统文件分页查询参数
 * <p>
 * 未传参的字段不参与过滤（动态条件），关键字（keyWord）模糊匹配文件原始名称与文件存储名称；
 * 排序字段由父类 {@link PageBasicQuery} 的 ascName/descName 指定，仅支持白名单字段（见 SysFileDaoImpl）
 *
 * @author xht
 **/
@Data
@Schema(description = "系统文件分页查询参数")
public class SysFileQuery extends PageBasicQuery {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件存储名称
     */
    @Schema(description = "文件存储名称")
    private String fileStorageName;

    /**
     * 文件存储key
     */
    @Schema(description = "文件存储key")
    private String fileStorageKey;

    /**
     * 文件状态
     */
    @Schema(description = "文件状态")
    private FileStatusEnum fileStatus;


}
