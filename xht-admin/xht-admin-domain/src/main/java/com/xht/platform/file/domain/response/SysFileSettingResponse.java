package com.xht.platform.file.domain.response;

import com.xht.platform.file.enums.ConfigOverrideDirectionEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 描述： 文件全局配置响应
 *
 * @author xht
 **/
@Data
@Schema(description = "文件全局配置响应")
public class SysFileSettingResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "配置键")
    private String settingKey;

    @Schema(description = "当前值")
    private String settingValue;

    @Schema(description = "值类型")
    private String valueType;

    @Schema(description = "分组")
    private String settingGroup;

    @Schema(description = "配置名称")
    private String settingName;

    @Schema(description = "说明")
    private String description;

    @Schema(description = "默认值")
    private String defaultValue;

    @Schema(description = "最小值")
    private String minValue;

    @Schema(description = "最大值")
    private String maxValue;

    @Schema(description = "是否允许用户覆盖")
    private Integer userOverridable;

    @Schema(description = "覆盖方向")
    private ConfigOverrideDirectionEnum overrideDirection;

    @Schema(description = "用户覆盖最小值")
    private String overrideMin;

    @Schema(description = "用户覆盖最大值")
    private String overrideMax;

    @Schema(description = "用户覆盖枚举")
    private String overrideOptions;

    @Schema(description = "覆盖提示")
    private String overrideHint;

    @Schema(description = "乐观锁版本")
    private Integer version;

}
