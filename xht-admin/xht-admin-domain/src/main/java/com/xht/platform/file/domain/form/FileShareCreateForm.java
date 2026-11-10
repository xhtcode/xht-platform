package com.xht.platform.file.domain.form;

import com.xht.platform.file.enums.ShareModeEnum;
import com.xht.platform.file.enums.SharePermissionEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 描述： 创建文件分享请求
 *
 * @author xht
 **/
@Data
@Schema(description = "创建文件分享请求")
public class FileShareCreateForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 预览ID
     */
    @Schema(description = "预览ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String previewId;

    /**
     * 分享模式
     */
    @Schema(description = "分享模式 LINK/USER", requiredMode = Schema.RequiredMode.REQUIRED)
    private ShareModeEnum shareMode;

    /**
     * 分享权限
     */
    @Schema(description = "分享权限 VIEW/VIEW_DOWNLOAD/DOWNLOAD", requiredMode = Schema.RequiredMode.REQUIRED)
    private SharePermissionEnum permission;

    /**
     * 有效期天数
     */
    @Schema(description = "有效期天数，默认取用途/全局配置")
    private Integer expireDays;

    /**
     * 是否需要提取码
     */
    @Schema(description = "是否需要提取码")
    private Boolean requireCode;

    /**
     * 自定义提取码；requireCode=true 且为空时随机生成
     */
    @Schema(description = "自定义提取码")
    private String accessCode;

    /**
     * 访问次数上限，仅 LINK 生效
     */
    @Schema(description = "访问次数上限，0=不限")
    private Integer maxAccessCount;

    /**
     * 接收人类型 USER/DEPT/ROLE
     */
    @Schema(description = "接收人类型 USER/DEPT/ROLE")
    private String receiverType;

    /**
     * 接收人ID集合
     */
    @Schema(description = "接收人ID集合")
    private List<Long> receiverIds;

    /**
     * 是否发送站内信
     */
    @Schema(description = "是否发送站内信")
    private Boolean needNotice;

}
