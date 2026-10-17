package com.xht.platform.file.thumbnail;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import java.io.Serial;
import java.io.Serializable;

/**
 * 描述： 缩略图生成配置
 *
 * @author xht
 **/
@Data
@Component
@ConfigurationProperties("xht.file.thumbnail")
public class ThumbnailProperties implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 是否启用缩略图生成
     */
    private boolean enabled = true;

    /**
     * 缩略图目标宽度（等比缩放，不超过该值）
     */
    private int width = 300;

    /**
     * 缩略图目标高度（等比缩放，不超过该值）
     */
    private int height = 300;

    /**
     * 缩略图 JPEG 压缩质量（0~1）
     */
    private float quality = 0.75f;

    /**
     * 同步生成阈值：原图小于等于该大小时同步生成（上传接口内完成），大于时异步生成
     */
    @DataSizeUnit(DataUnit.MEGABYTES)
    private DataSize syncThreshold = DataSize.ofMegabytes(2);

    /**
     * 源图下采样阈值：源图边长超过该值时按倍数抽样读取，避免大图解码 OOM
     */
    private int maxSourceDimension = 4096;

}
