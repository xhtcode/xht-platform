package com.xht.generate.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 表扩展配置
 *
 * @author xht
 **/
@Data
@Schema(description = "表扩展配置")
public class TableExtConfig {

    /**
     * 数据库连接URL
     */
    @Schema(description = "数据库连接URL")
    @NotBlank(message = "数据库连接URL不能为空")
    private String url;

    /**
     * 模块名称
     */
    @Schema(description = "模块名称")
    @NotBlank(message = "模块名称不能为空")
    private String moduleName;

    /**
     * 服务名称
     */
    @Schema(description = "服务名称")
    @NotBlank(message = "服务名称不能为空")
    private String serviceName;

    /**
     * 授权前缀
     */
    @Schema(description = "授权前缀")
    @NotBlank(message = "授权前缀不能为空")
    private String authorizationPrefix;

}
