package com.xht.framework.common.domain;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Setter;

import java.util.Map;

/**
 * 描述：
 *
 * @author xht
 **/
@Schema(name = "ErrorR", description = "错误响应结果")
public non-sealed class ErrorR<T> extends R<T> {

    @Setter
    @JsonAnyGetter
    @JsonAnySetter
    private Map<String, Object> extra;

    public ErrorR(R<T> r) {
        super(r.getCode(), r.getMsg(), r.getData(), r.getDataType(), r.getTraceId());
    }

    public ErrorR(R<T> r, Map<String, Object> extra) {
        super(r.getCode(), r.getMsg(), r.getData(), r.getDataType(), r.getTraceId());
        this.extra = extra;
    }

    public void setExtra(String key, Object value) {
        this.extra.put(key, value);
    }
}
