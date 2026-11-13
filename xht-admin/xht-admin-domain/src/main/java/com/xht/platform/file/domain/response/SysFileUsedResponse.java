package com.xht.platform.file.domain.response;

import com.xht.platform.file.enums.FileAccessScopeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 描述： 文件用途配置响应
 *
 * @author xht
 **/
@Data
@Schema(description = "文件用途配置响应")
public class SysFileUsedResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "用途编码")
    private String usedCode;

    @Schema(description = "用途名称")
    private String usedName;

    @Schema(description = "说明")
    private String description;

    @Schema(description = "是否内置")
    private Integer builtIn;

    @Schema(description = "是否启用")
    private Integer enabled;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "默认访问范围")
    private FileAccessScopeEnum accessScope;

    @Schema(description = "是否允许设为 public")
    private Integer publicAllowed;

    @Schema(description = "下载闸门")
    private Integer downloadAllowed;

    @Schema(description = "打印闸门")
    private Integer printAllowed;

    @Schema(description = "分享闸门")
    private Integer shareAllowed;

    @Schema(description = "默认分享天数")
    private Integer shareExpireDays;

    @Schema(description = "分享访问次数上限")
    private Integer shareMaxAccessCount;

    @Schema(description = "引用文件数")
    private Long fileCount;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

}
