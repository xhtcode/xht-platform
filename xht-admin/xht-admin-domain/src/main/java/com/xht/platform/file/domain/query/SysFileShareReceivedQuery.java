package com.xht.platform.file.domain.query;

import com.xht.framework.common.domain.query.PageBasicQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 描述： 分享给我的分页查询参数
 *
 * @author xht
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "分享给我的分页查询参数")
public class SysFileShareReceivedQuery extends PageBasicQuery {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件名关键字
     */
    @Schema(description = "文件名关键字")
    private String fileName;

    /**
     * 已读状态：0未读 1已读
     */
    @Schema(description = "已读状态")
    private Integer readStatus;

    /**
     * 是否包含已过期/已失效
     */
    @Schema(description = "是否包含已过期/已失效")
    private Boolean includeInvalid;

    /**
     * 接收人ID，服务端填充，不对外暴露
     */
    @Schema(hidden = true)
    private Long receiverId;

}
