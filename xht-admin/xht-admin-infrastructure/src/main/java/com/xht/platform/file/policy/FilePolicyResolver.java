package com.xht.platform.file.policy;

import com.xht.framework.utils.StringUtils;
import com.xht.platform.file.dao.SysFileTypeDao;
import com.xht.platform.file.domain.access.FilePolicy;
import com.xht.platform.file.entity.SysFileEntity;
import com.xht.platform.file.entity.SysFileTypeEntity;
import com.xht.platform.file.entity.SysFileUsedEntity;
import com.xht.platform.file.enums.FileAccessScopeEnum;
import com.xht.platform.file.enums.ImageModeEnum;
import com.xht.platform.file.enums.PreviewSourceEnum;
import com.xht.platform.file.enums.PreviewTypeEnum;
import com.xht.platform.file.enums.WatermarkLevelEnum;
import com.xht.platform.file.service.SysFileSettingService;
import com.xht.platform.file.service.SysFileUsedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * 描述： 文件策略解析器
 * <p>按“文件级覆盖 &gt; 用途（业务）/ 后缀（技术）&gt; 全局 &gt; 代码默认”解析最终生效策略</p>
 *
 * @author xht
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class FilePolicyResolver {

    /**
     * 全局配置：默认用途
     */
    private static final String KEY_DEFAULT_USED = "file.default-used";
    /**
     * 全局配置：默认允许下载
     */
    private static final String KEY_DOWNLOAD_DEFAULT = "download.default-allow";
    /**
     * 全局配置：默认允许打印
     */
    private static final String KEY_PRINT_DEFAULT = "print.default-allow";
    /**
     * 全局配置：分享总开关
     */
    private static final String KEY_SHARE_ENABLED = "share.enabled";
    /**
     * 全局配置：分享默认有效期
     */
    private static final String KEY_SHARE_EXPIRE_DAYS = "share.default-expire-days";
    /**
     * 全局配置：分享默认访问次数
     */
    private static final String KEY_SHARE_MAX_ACCESS = "share.default-max-access-count";
    /**
     * 全局配置：默认水印等级
     */
    private static final String KEY_WATERMARK_LEVEL = "watermark.default-level";
    /**
     * 全局配置：默认图片模式
     */
    private static final String KEY_IMAGE_MODE = "image.default-mode";

    private final SysFileUsedService sysFileUsedService;

    private final SysFileTypeDao sysFileTypeDao;

    private final SysFileSettingService sysFileSettingService;

    /**
     * 解析文件最终生效策略
     *
     * @param file 文件实体
     * @return 生效策略
     */
    public FilePolicy resolve(SysFileEntity file) {
        String usedCode = StringUtils.hasText(file.getFileUsed())
                ? file.getFileUsed()
                : sysFileSettingService.getString(KEY_DEFAULT_USED, "temp");
        SysFileUsedEntity used = sysFileUsedService.findByCode(usedCode);
        if (Objects.isNull(used)) {
            used = sysFileUsedService.findByCode("other");
            log.warn("文件用途 [{}] 未配置，按 other 兜底，fileId={}", usedCode, file.getId());
        }
        SysFileTypeEntity type = sysFileTypeDao.findConfigBySuffix(file.getFileSuffix());
        Map<String, Object> fileConfig = Objects.isNull(file.getFileConfig()) ? Map.of() : file.getFileConfig();

        PreviewTypeEnum previewType = Objects.isNull(type) || Objects.isNull(type.getPreviewType())
                ? PreviewTypeEnum.OTHER : type.getPreviewType();
        PreviewSourceEnum previewSource = Objects.isNull(type) || Objects.isNull(type.getPreviewSource())
                ? PreviewSourceEnum.ORIGINAL : type.getPreviewSource();
        String convertTarget = Objects.isNull(type) ? null : type.getConvertTarget();

        boolean usedDownload = used == null || !Objects.equals(used.getDownloadAllowed(), 0);
        boolean usedPrint = used == null || !Objects.equals(used.getPrintAllowed(), 0);
        boolean usedShare = used == null || !Objects.equals(used.getShareAllowed(), 0);
        boolean publicAllowed = used != null && Objects.equals(used.getPublicAllowed(), 1);

        boolean downloadAllowed = resolveTightenBoolean(fileConfig, "downloadAllowed",
                KEY_DOWNLOAD_DEFAULT, usedDownload && sysFileSettingService.getBoolean(KEY_DOWNLOAD_DEFAULT, true));
        boolean printAllowed = resolveTightenBoolean(fileConfig, "printAllowed",
                KEY_PRINT_DEFAULT, usedPrint && sysFileSettingService.getBoolean(KEY_PRINT_DEFAULT, true));
        boolean shareAllowed = resolveTightenBoolean(fileConfig, "shareAllowed",
                KEY_SHARE_ENABLED, usedShare && sysFileSettingService.getBoolean(KEY_SHARE_ENABLED, true));

        int defaultShareExpire = used != null && used.getShareExpireDays() != null
                ? used.getShareExpireDays()
                : sysFileSettingService.getInt(KEY_SHARE_EXPIRE_DAYS, 7);
        Integer shareExpireDays = resolveTightenInt(fileConfig, "shareExpireDays",
                KEY_SHARE_EXPIRE_DAYS, defaultShareExpire);

        int defaultMaxAccess = used != null && used.getShareMaxAccessCount() != null
                ? used.getShareMaxAccessCount()
                : sysFileSettingService.getInt(KEY_SHARE_MAX_ACCESS, 0);
        Integer shareMaxAccess = resolveTightenInt(fileConfig, "shareMaxAccessCount",
                KEY_SHARE_MAX_ACCESS, defaultMaxAccess);

        WatermarkLevelEnum usedWatermark = WatermarkLevelEnum.getByValue(
                used == null ? null : used.getWatermarkLevel());
        if (usedWatermark == null) {
            usedWatermark = WatermarkLevelEnum.getByValue(
                    sysFileSettingService.getString(KEY_WATERMARK_LEVEL, "l1"));
        }
        WatermarkLevelEnum watermarkLevel = resolveWatermark(fileConfig, usedWatermark);

        ImageModeEnum usedImageMode = ImageModeEnum.getByValue(used == null ? null : used.getImageMode());
        if (usedImageMode == null) {
            usedImageMode = ImageModeEnum.getByValue(
                    sysFileSettingService.getString(KEY_IMAGE_MODE, "original"));
        }
        ImageModeEnum imageMode = resolveImageMode(fileConfig, usedImageMode);

        // 图片按用途/文件级策略决定是否转换；Office 等非图片类型走后缀配置
        if (PreviewTypeEnum.IMAGE == previewType) {
            if (ImageModeEnum.PDF == imageMode) {
                previewType = PreviewTypeEnum.PDF;
                previewSource = PreviewSourceEnum.CONVERT;
                convertTarget = "pdf";
            } else if (ImageModeEnum.WEBP == imageMode) {
                previewSource = PreviewSourceEnum.CONVERT;
                convertTarget = "webp";
            } else if (isIncompatibleImage(file.getFileSuffix())) {
                previewSource = PreviewSourceEnum.CONVERT;
                convertTarget = "png";
            }
        }

        FileAccessScopeEnum accessScope = Objects.nonNull(file.getAccessScope())
                ? file.getAccessScope()
                : (used == null ? FileAccessScopeEnum.PRIVATE : used.getAccessScope());
        if (!publicAllowed) {
            accessScope = FileAccessScopeEnum.PRIVATE;
        }

        return FilePolicy.builder()
                .usedCode(usedCode)
                .usedName(used == null ? usedCode : used.getUsedName())
                .previewType(previewType)
                .previewSource(previewSource)
                .convertTarget(convertTarget)
                .needConvert(PreviewSourceEnum.CONVERT == previewSource && StringUtils.hasText(convertTarget))
                .accessScope(accessScope)
                .publicAllowed(publicAllowed)
                .downloadAllowed(downloadAllowed)
                .printAllowed(printAllowed)
                .shareAllowed(shareAllowed)
                .shareExpireDays(shareExpireDays)
                .shareMaxAccessCount(shareMaxAccess)
                .watermarkLevel(watermarkLevel)
                .imageMode(imageMode)
                .build();
    }

    /**
     * 是否浏览器不兼容的图片格式
     *
     * @param fileSuffix 文件后缀
     * @return true：需要转换
     */
    private boolean isIncompatibleImage(String fileSuffix) {
        if (!StringUtils.hasText(fileSuffix)) {
            return false;
        }
        String suffix = fileSuffix.toLowerCase();
        return "bmp".equals(suffix) || "tif".equals(suffix) || "tiff".equals(suffix)
                || "heic".equals(suffix) || "heif".equals(suffix);
    }

    /**
     * 解析只能收紧的布尔覆盖
     * <p>用户只能把 true 改成 false，不能把 false 改成 true</p>
     *
     * @param fileConfig  文件级配置
     * @param key         文件级 key
     * @param globalKey   全局配置 key，用于判断是否允许覆盖
     * @param baseValue   基础值
     * @return 最终值
     */
    private boolean resolveTightenBoolean(Map<String, Object> fileConfig, String key, String globalKey, boolean baseValue) {
        if (!baseValue) {
            return false;
        }
        if (!sysFileSettingService.isUserOverridable(globalKey) || !fileConfig.containsKey(key)) {
            return true;
        }
        return parseBoolean(fileConfig.get(key), true);
    }

    /**
     * 解析只能收紧的整数覆盖
     *
     * @param fileConfig 文件级配置
     * @param key        文件级 key
     * @param globalKey  全局配置 key
     * @param baseValue  基础值
     * @return 最终值
     */
    private Integer resolveTightenInt(Map<String, Object> fileConfig, String key, String globalKey, int baseValue) {
        if (!sysFileSettingService.isUserOverridable(globalKey) || !fileConfig.containsKey(key)) {
            return baseValue;
        }
        Integer value = parseInteger(fileConfig.get(key));
        if (value == null || value < 0) {
            return baseValue;
        }
        if (baseValue == 0) {
            return value;
        }
        if (value == 0) {
            return baseValue;
        }
        return Math.min(value, baseValue);
    }

    /**
     * 解析水印等级，只允许调高
     *
     * @param fileConfig 文件级配置
     * @param baseValue  基础等级
     * @return 最终等级
     */
    private WatermarkLevelEnum resolveWatermark(Map<String, Object> fileConfig, WatermarkLevelEnum baseValue) {
        WatermarkLevelEnum base = baseValue == null ? WatermarkLevelEnum.L1 : baseValue;
        if (!sysFileSettingService.isUserOverridable(KEY_WATERMARK_LEVEL) || !fileConfig.containsKey("watermarkLevel")) {
            return base;
        }
        WatermarkLevelEnum value = WatermarkLevelEnum.getByValue(String.valueOf(fileConfig.get("watermarkLevel")));
        return WatermarkLevelEnum.max(base, value);
    }

    /**
     * 解析图片模式
     *
     * @param fileConfig 文件级配置
     * @param baseValue  基础模式
     * @return 最终模式
     */
    private ImageModeEnum resolveImageMode(Map<String, Object> fileConfig, ImageModeEnum baseValue) {
        ImageModeEnum base = baseValue == null ? ImageModeEnum.ORIGINAL : baseValue;
        if (!sysFileSettingService.isUserOverridable(KEY_IMAGE_MODE) || !fileConfig.containsKey("imageMode")) {
            return base;
        }
        ImageModeEnum value = ImageModeEnum.getByValue(String.valueOf(fileConfig.get("imageMode")));
        return value == null ? base : value;
    }

    /**
     * 解析布尔值
     *
     * @param value        原始值
     * @param defaultValue 默认值
     * @return 布尔值
     */
    private boolean parseBoolean(Object value, boolean defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Boolean b) {
            return b;
        }
        if (value instanceof Number n) {
            return n.intValue() == 1;
        }
        String text = String.valueOf(value).trim();
        if ("true".equalsIgnoreCase(text) || "1".equals(text)) {
            return true;
        }
        if ("false".equalsIgnoreCase(text) || "0".equals(text)) {
            return false;
        }
        return defaultValue;
    }

    /**
     * 解析整数
     *
     * @param value 原始值
     * @return 整数，非法返回 null
     */
    private Integer parseInteger(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number n) {
            return n.intValue();
        }
        try {
            return Integer.parseInt(String.valueOf(value).trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
