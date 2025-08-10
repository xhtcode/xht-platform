package com.xht.generate.domain.request;

import com.xht.framework.core.domain.request.FormRequest;
import com.xht.framework.web.validation.Groups;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 数据源表单请求参数
 *
 * @author xht
 **/
@Data
@Schema(description = "数据源表单请求参数")
public class GenDataSourceFormRequest extends FormRequest {

    /**
     * 唯一标识
     */
    @Null(message = "唯一标识必须为空", groups = {Groups.Create.class})
    @NotNull(message = "唯一标识参数不合法", groups = {Groups.Update.class})
    @Positive(message = "唯一标识参数不合法", groups = { Groups.Update.class})
    @Schema(description = "唯一标识", example = "101")
    private Long id;

    /**
     * 数据源名称
     */
    @Schema(description = "数据源名称")
    private String name;

    /**
     * 数据库类型（MySQL/Oracle）
     */
    @Schema(description = "数据库类型")
    private String dbType;

    /**
     * 数据库地址
     */
    @Schema(description = "数据库地址")
    private String url;


    /**
     * 连接测试结果（success/fail）
     */
    @Schema(description = "连接测试结果")
    private String testResult;

    /**
     * 最后测试时间
     */
    @Schema(description = "最后测试时间")
    private LocalDateTime lastTestTime;
}
