package com.xht.platform.file.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 描述： 文件全局配置更新请求
 *
 * @author xht
 **/
@Data
@Schema(description = "文件全局配置更新请求")
public class SysFileSettingUpdateForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "配置项列表")
    private List<Item> items;

    /**
     * 配置项
     */
    @Data
    @Schema(description = "配置项")
    public static class Item implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        @Schema(description = "配置键")
        private String key;

        @Schema(description = "新值")
        private String value;

        @Schema(description = "版本号")
        private Integer version;

    }

}
