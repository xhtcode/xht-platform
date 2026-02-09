package com.xht.framework.core.domain.query;

import com.xht.framework.core.domain.IRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 查询请求接口
 *
 * @author xht
 **/
@Setter
@Getter
@Schema(description = "查询参数")
public abstract class BasicQuery implements IRequest, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 是否快速查询
     */
    @Schema(name = "quick", description = "是否快速查询")
    private boolean quick;

    /**
     * 关键字
     */
    @Schema(name = "keyWord", description = "关键字")
    private String keyWord;

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
