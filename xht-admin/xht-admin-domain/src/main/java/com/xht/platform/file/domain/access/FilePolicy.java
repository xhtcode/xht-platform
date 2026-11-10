package com.xht.platform.file.domain.access;

import com.xht.platform.file.enums.FileAccessScopeEnum;
import com.xht.platform.file.enums.ImageModeEnum;
import com.xht.platform.file.enums.PreviewSourceEnum;
import com.xht.platform.file.enums.PreviewTypeEnum;
import com.xht.platform.file.enums.WatermarkLevelEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 描述： 文件最终生效策略
 * <p>由文件级覆盖、用途配置、后缀配置、全局配置逐层解析得到</p>
 *
 * @author xht
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilePolicy implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用途编码
     */
    private String usedCode;

    /**
     * 用途名称
     */
    private String usedName;

    /**
     * 预览类型
     */
    private PreviewTypeEnum previewType;

    /**
     * 预览来源
     */
    private PreviewSourceEnum previewSource;

    /**
     * 转换目标格式
     */
    private String convertTarget;

    /**
     * 是否需要转换
     */
    private boolean needConvert;

    /**
     * 访问范围
     */
    private FileAccessScopeEnum accessScope;

    /**
     * 用途是否允许公开
     */
    private boolean publicAllowed;

    /**
     * 下载闸门
     */
    private boolean downloadAllowed;

    /**
     * 打印闸门
     */
    private boolean printAllowed;

    /**
     * 分享闸门
     */
    private boolean shareAllowed;

    /**
     * 分享有效期（天）
     */
    private Integer shareExpireDays;

    /**
     * 分享访问次数上限，0=不限
     */
    private Integer shareMaxAccessCount;

    /**
     * 水印等级
     */
    private WatermarkLevelEnum watermarkLevel;

    /**
     * 图片处理模式
     */
    private ImageModeEnum imageMode;

    /**
     * 是否为图片类型
     *
     * @return true：图片
     */
    public boolean isImage() {
        return PreviewTypeEnum.IMAGE == previewType;
    }

    /**
     * 是否支持在线预览
     *
     * @return true：支持
     */
    public boolean isPreviewable() {
        return PreviewTypeEnum.OTHER != previewType;
    }

}
