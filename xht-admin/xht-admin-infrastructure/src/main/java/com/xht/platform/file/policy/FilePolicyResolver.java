package com.xht.platform.file.policy;

import com.xht.framework.utils.SafeGetUtil;
import com.xht.framework.utils.StringUtils;
import com.xht.platform.file.dao.SysFileTypeDao;
import com.xht.platform.file.domain.access.FilePolicy;
import com.xht.platform.file.entity.SysFileEntity;
import com.xht.platform.file.entity.SysFileTypeEntity;
import com.xht.platform.file.entity.SysFileUsedEntity;
import com.xht.platform.file.enums.*;
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

    private final SysFileUsedService sysFileUsedService;

    private final SysFileTypeDao sysFileTypeDao;

    private final SysFileSettingService sysFileSettingService;

    /**
     * 解析文件最终生效策略
     *
     * @param sysFileEntity 文件实体
     * @return 生效策略
     */
    public FilePolicy resolve(SysFileEntity sysFileEntity) {
        String usedCode = StringUtils.emptyToDefault(sysFileEntity.getFileUsed(), "temp");
        SysFileUsedEntity used = sysFileUsedService.findByCode(usedCode);
        if (Objects.isNull(used)) {
            used = sysFileUsedService.findByCode("other");
            log.warn("文件用途 [{}] 未配置，按 other 兜底，fileId={}", usedCode, sysFileEntity.getId());
        }
        SysFileTypeEntity sysFileTypeEntity = sysFileTypeDao.findConfigBySuffix(sysFileEntity.getFileSuffix());
        Map<String, Object> fileConfig = Objects.isNull(sysFileEntity.getFileConfig()) ? Map.of() : sysFileEntity.getFileConfig();
        PreviewTypeEnum previewType = SafeGetUtil.getSafe(sysFileTypeEntity, SysFileTypeEntity::getPreviewType, () -> PreviewTypeEnum.OTHER);
        boolean usedDownload = Objects.equals(used.getDownloadAllowed(), 0);
        boolean usedPrint = Objects.equals(used.getPrintAllowed(), 0);
        boolean usedShare = Objects.equals(used.getShareAllowed(), 0);
        boolean publicAllowed = Objects.equals(used.getPublicAllowed(), 1);
        boolean downloadAllowed = resolveTightenBoolean(fileConfig, "downloadAllowed",
                KEY_DOWNLOAD_DEFAULT, usedDownload && sysFileSettingService.getBoolean(KEY_DOWNLOAD_DEFAULT, true));
        boolean printAllowed = resolveTightenBoolean(fileConfig, "printAllowed",
                KEY_PRINT_DEFAULT, usedPrint && sysFileSettingService.getBoolean(KEY_PRINT_DEFAULT, true));
        boolean shareAllowed = resolveTightenBoolean(fileConfig, "shareAllowed",
                KEY_SHARE_ENABLED, usedShare && sysFileSettingService.getBoolean(KEY_SHARE_ENABLED, true));
        int defaultShareExpire = SafeGetUtil.getSafe(used, SysFileUsedEntity::getShareExpireDays, () -> sysFileSettingService.getInt(KEY_SHARE_EXPIRE_DAYS, 7));
        Integer shareExpireDays = resolveTightenInt(fileConfig, "shareExpireDays",
                KEY_SHARE_EXPIRE_DAYS, defaultShareExpire);

        int defaultMaxAccess = used.getShareMaxAccessCount() != null
                ? used.getShareMaxAccessCount()
                : sysFileSettingService.getInt(KEY_SHARE_MAX_ACCESS, 0);
        Integer shareMaxAccess = resolveTightenInt(fileConfig, "shareMaxAccessCount",
                KEY_SHARE_MAX_ACCESS, defaultMaxAccess);

        FileAccessScopeEnum accessScope = Objects.nonNull(sysFileEntity.getAccessScope())
                ? sysFileEntity.getAccessScope()
                : used.getAccessScope();
        if (!publicAllowed) {
            accessScope = FileAccessScopeEnum.PRIVATE;
        }
        return FilePolicy.builder()
                .usedCode(usedCode)
                .usedName(used.getUsedName())
                .previewType(previewType)
                .accessScope(accessScope)
                .publicAllowed(publicAllowed)
                .downloadAllowed(downloadAllowed)
                .printAllowed(printAllowed)
                .shareAllowed(shareAllowed)
                .shareExpireDays(shareExpireDays)
                .shareMaxAccessCount(shareMaxAccess)
                .build();
    }

    /**
     * 解析只能收紧的布尔覆盖
     * <p>用户只能把 true 改成 false，不能把 false 改成 true</p>
     *
     * @param fileConfig 文件级配置
     * @param key        文件级 key
     * @param globalKey  全局配置 key，用于判断是否允许覆盖
     * @param baseValue  基础值
     * @return 最终值
     */
    private boolean resolveTightenBoolean(Map<String, Object> fileConfig, String key, String globalKey, boolean baseValue) {
        if (!baseValue) {
            return false;
        }
        if (sysFileSettingService.isUserOverridable(globalKey) || !fileConfig.containsKey(key)) {
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
        if (sysFileSettingService.isUserOverridable(globalKey) || !fileConfig.containsKey(key)) {
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
