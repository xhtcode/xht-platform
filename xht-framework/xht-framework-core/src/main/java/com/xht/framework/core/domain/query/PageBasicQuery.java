package com.xht.framework.core.domain.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * 描述 ：通用分页请求实体
 *
 * @author xht
 **/
@Setter
@Getter
@Schema(description = "通用分页请求实体")
public abstract class PageBasicQuery extends BasicQuery {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 当前页
     */
    @Schema(name = "current", description = "当前页参数", defaultValue = "0")
    private int current;

    /**
     * 每页显示条数
     */
    @Schema(name = "size", description = "每页显示条数", defaultValue = "10")
    private int size;

    /**
     * 正序排序的字段名
     */
    @Schema(name = "ascName", description = "正序排序的字段名")
    private String ascName;

    /**
     * 倒序排序字段名
     */
    @Schema(name = "descName", description = "倒序排序字段名")
    private String descName;

}
