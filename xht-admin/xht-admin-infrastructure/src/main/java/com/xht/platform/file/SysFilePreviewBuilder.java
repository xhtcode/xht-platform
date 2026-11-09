package com.xht.platform.file;

import com.xht.framework.utils.AssertUtils;
import com.xht.platform.file.domain.response.SysFilePreviewResponse;
import com.xht.platform.file.entity.SysFileEntity;
import com.xht.platform.file.enums.FileInitStatusEnum;
import com.xht.platform.file.enums.PreviewTypeEnum;
import lombok.Getter;

import java.util.Objects;

/**
 * 文件预览响应建造者
 * <p>用于链式组装文件预览返回对象 {@link SysFilePreviewResponse}，
 * 从 {@link SysFileEntity} 读取文件基础信息，配合外部传入的预览状态、预览模式、预览ID组装返回结果</p>
 *
 * @author xht
 **/
@Getter
public class SysFilePreviewBuilder {

    /**
     * 预览处理状态
     */
    private FileInitStatusEnum initStatus;

    /**
     * 预览模式
     */
    private PreviewTypeEnum previewMode;

    /**
     * 预览唯一标识ID，前端预览页面、预览服务使用该标识拉取预览资源
     */
    private String previewId;

    /**
     * 私有构造，仅允许通过 builder() 方法创建构建实例
     */
    private SysFilePreviewBuilder() {
    }

    /**
     * 设置预览处理状态
     *
     * @param initStatus 预览状态枚举
     * @return 当前建造者对象，支持链式调用
     */
    public SysFilePreviewBuilder initStatus(FileInitStatusEnum initStatus) {
        if (!Objects.equals(this.initStatus, FileInitStatusEnum.SUCCESS) && Objects.equals(initStatus, FileInitStatusEnum.SUCCESS)) {
            this.initStatus = initStatus;
        }
        return this;
    }

    /**
     * 设置预览模式
     *
     * @param previewMode 预览模式枚举
     * @return 当前建造者对象，支持链式调用
     */
    public SysFilePreviewBuilder previewMode(PreviewTypeEnum previewMode) {
        this.previewMode = previewMode;
        return this;
    }

    /**
     * 设置预览唯一ID
     *
     * @param previewId 预览唯一标识
     * @return 当前建造者对象，支持链式调用
     */
    public SysFilePreviewBuilder previewId(String previewId) {
        this.previewId = previewId;
        return this;
    }

    /**
     * 获取建造者实例
     *
     * @return 新的建造者对象
     */
    public static SysFilePreviewBuilder builder() {
        return new SysFilePreviewBuilder();
    }

    /**
     * 组装并生成预览响应实体
     * <p>文件基础信息（文件名、原始名、大小、后缀等）自动从 SysFileEntity 填充；
     * 动态预览相关字段（状态、预览模式、预览ID）由建造者链式设置传入</p>
     *
     * @param sysFileEntity 文件数据库实体，不能为空
     * @return 组装完成的预览响应对象
     */
    public SysFilePreviewResponse build(SysFileEntity sysFileEntity) {
        AssertUtils.notNull(sysFileEntity, "文件实体不能为空");
        AssertUtils.notNull(initStatus, "预览状态【initStatus】不能为空");
        AssertUtils.notNull(previewMode, "预览模式【previewMode】不能为空");
        SysFilePreviewResponse response = new SysFilePreviewResponse();
        response.setInitStatus(this.initStatus);
        response.setFileName(sysFileEntity.getFileStorageName());
        response.setFileOriginalName(sysFileEntity.getFileOriginalName());
        response.setFileSize(sysFileEntity.getFileSize());
        response.setFileSizeInfo(sysFileEntity.getFileSizeInfo());
        response.setFileSuffix(sysFileEntity.getFileSuffix());
        response.setPreviewMode(this.previewMode);
        response.setPreviewId(this.previewId);
        return response;
    }

}
