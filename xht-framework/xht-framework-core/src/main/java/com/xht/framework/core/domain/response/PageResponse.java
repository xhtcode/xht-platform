package com.xht.framework.core.domain.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xht.framework.core.jackson.databind.CustomLongSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 描述 ：分页信息响应实体
 *
 * @author xht
 **/
@Data
@Schema(description = "分页信息响应实体")
public final class PageResponse<T> extends BasicResponse {

    /**
     * 当前页
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    @Schema(description = "当前页")
    private long current;

    /**
     * 每页显示条数
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    @Schema(description = "每页显示条数")
    private long size;

    /**
     * 总页数
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    @Schema(description = "总页数")
    private long pages;

    /**
     * 总条目数
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    @Schema(description = "总条目数")
    private long total;

    /**
     * 结果集
     */
    @Schema(description = "结果集")
    private List<T> records;

}
