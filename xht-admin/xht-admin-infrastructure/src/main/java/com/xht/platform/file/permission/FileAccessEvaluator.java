package com.xht.platform.file.permission;

import com.xht.framework.utils.StringUtils;
import com.xht.platform.file.domain.access.FileAccessDecision;
import com.xht.platform.file.domain.access.FileAccessSubject;
import com.xht.platform.file.domain.access.FilePolicy;
import com.xht.platform.file.entity.SysFileEntity;
import com.xht.platform.file.enums.FileAccessScopeEnum;
import com.xht.platform.file.enums.FileAccessSubjectTypeEnum;
import com.xht.platform.file.enums.SharePermissionEnum;
import com.xht.platform.file.policy.FilePolicyResolver;
import com.xht.platform.file.service.SysFileSettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * 描述： 文件访问决策器
 * <p>组合“归属/管理员、公开范围、分享权限、业务提供者、配置闸门”计算最终能力</p>
 *
 * @author xht
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class FileAccessEvaluator {

    /**
     * 全局配置：上传人/管理员绕过下载打印闸门
     */
    private static final String KEY_OWNER_OVERRIDE = "download.owner-override";
    /**
     * 全局配置：匿名访问公开文件允许下载
     */
    private static final String KEY_ANON_PUBLIC_DOWNLOAD = "download.anonymous-public";
    /**
     * 全局配置：登录用户访问公开文件允许下载
     */
    private static final String KEY_LOGGED_PUBLIC_DOWNLOAD = "download.public-logged-in";

    private final FilePolicyResolver filePolicyResolver;

    private final SysFileSettingService sysFileSettingService;

    private final List<FilePermissionProvider> permissionProviders;

    /**
     * 计算访问决策
     *
     * @param file    文件实体
     * @param subject 访问主体
     * @return 决策
     */
    public FileAccessDecision decide(SysFileEntity file, FileAccessSubject subject) {
        if (Objects.isNull(subject) || Objects.isNull(subject.getType())) {
            return FileAccessDecision.denyAll();
        }
        FilePolicy policy = filePolicyResolver.resolve(file);
        return switch (subject.getType()) {
            case USER -> decideUser(file, policy, subject);
            case ANON -> decideAnonymous(policy);
            case SHARE_LINK, SHARE_USER -> decideShare(policy, subject);
        };
    }

    /**
     * 登录用户决策
     *
     * @param file    文件
     * @param policy  策略
     * @param subject 主体
     * @return 决策
     */
    private FileAccessDecision decideUser(SysFileEntity file, FilePolicy policy, FileAccessSubject subject) {
        boolean owner = StringUtils.hasText(subject.getUserName())
                && Objects.equals(subject.getUserName(), file.getFileUploadUser());
        boolean admin = subject.isAdmin();
        boolean ownerOverride = sysFileSettingService.getBoolean(KEY_OWNER_OVERRIDE, true);

        if (owner || admin) {
            boolean download = policy.isDownloadAllowed() || ownerOverride;
            boolean print = policy.isPrintAllowed() || ownerOverride;
            return FileAccessDecision.builder()
                    .view(true)
                    .download(download)
                    .print(print)
                    .share(policy.isShareAllowed())
                    .build();
        }

        if (FileAccessScopeEnum.PUBLIC == policy.getAccessScope()) {
            boolean download = policy.isDownloadAllowed()
                    && sysFileSettingService.getBoolean(KEY_LOGGED_PUBLIC_DOWNLOAD, true);
            return FileAccessDecision.builder()
                    .view(true)
                    .download(download)
                    .print(download && policy.isPrintAllowed())
                    .share(false)
                    .build();
        }

        FileAccessDecision providerDecision = decideByProviders(file, subject);
        if (!providerDecision.hasAny()) {
            return FileAccessDecision.denyAll();
        }
        return FileAccessDecision.builder()
                .view(providerDecision.isView())
                .download(providerDecision.isDownload() && policy.isDownloadAllowed())
                .print(providerDecision.isPrint() && policy.isPrintAllowed())
                .share(providerDecision.isShare() && policy.isShareAllowed())
                .build();
    }

    /**
     * 匿名公开访问决策
     *
     * @param policy 策略
     * @return 决策
     */
    private FileAccessDecision decideAnonymous(FilePolicy policy) {
        if (FileAccessScopeEnum.PUBLIC != policy.getAccessScope() || !policy.isPublicAllowed()) {
            return FileAccessDecision.denyAll();
        }
        boolean download = policy.isDownloadAllowed()
                && sysFileSettingService.getBoolean(KEY_ANON_PUBLIC_DOWNLOAD, false);
        return FileAccessDecision.builder()
                .view(true)
                .download(download)
                .print(false)
                .share(false)
                .build();
    }

    /**
     * 分享访问决策
     *
     * @param policy  策略
     * @param subject 主体
     * @return 决策
     */
    private FileAccessDecision decideShare(FilePolicy policy, FileAccessSubject subject) {
        SharePermissionEnum permission = subject.getSharePermission();
        if (permission == null) {
            return FileAccessDecision.denyAll();
        }
        if (FileAccessSubjectTypeEnum.SHARE_USER == subject.getType() && subject.getReceiverId() == null) {
            return FileAccessDecision.denyAll();
        }
        return FileAccessDecision.builder()
                .view(permission.isPreview())
                .download(permission.isDownload() && policy.isDownloadAllowed())
                .print(permission.isPrint() && policy.isPrintAllowed())
                .share(false)
                .build();
    }

    /**
     * 汇总业务提供者决策
     *
     * @param file    文件
     * @param subject 主体
     * @return 交集决策
     */
    private FileAccessDecision decideByProviders(SysFileEntity file, FileAccessSubject subject) {
        FileAccessDecision decision = null;
        for (FilePermissionProvider provider : permissionProviders) {
            if (!provider.supports(file)) {
                continue;
            }
            FileAccessDecision current = provider.decide(file, subject);
            if (Objects.isNull(current)) {
                current = FileAccessDecision.denyAll();
            }
            decision = decision == null ? current : decision.intersect(current);
        }
        return decision == null ? FileAccessDecision.denyAll() : decision;
    }

}
