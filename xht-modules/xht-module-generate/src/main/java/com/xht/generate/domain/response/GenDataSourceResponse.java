package com.xht.generate.domain.response;

import com.xht.framework.core.domain.response.BasicResponse;
import com.xht.generate.constant.DataBaseTypeEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 数据源响应信息
 *
 * @author xht
 **/
@Data
@Schema(description = "数据源响应信息")
public class GenDataSourceResponse extends BasicResponse {

    /**
     * 数据源ID
     */
    @Schema(description = "数据源ID")
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
    private DataBaseTypeEnums dbType;

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
