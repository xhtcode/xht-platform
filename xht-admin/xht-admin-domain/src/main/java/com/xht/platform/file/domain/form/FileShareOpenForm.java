package com.xht.platform.file.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 描述： 打开分享请求
 *
 * @author xht
 **/
@Data
@Schema(description = "打开分享请求")
public class FileShareOpenForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 提取码
     */
    @Schema(description = "提取码；requireCode=true 时必填")
    private String accessCode;

}
