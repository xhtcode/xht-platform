package com.xht.platform.file.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.platform.common.file.core.FileMetadata;
import com.xht.platform.file.domain.form.SysFileForm;
import com.xht.platform.file.domain.response.SysFilePreviewResponse;
import com.xht.platform.file.domain.response.SysFileResponse;
import com.xht.platform.file.entity.SysFileEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.Optional;

/**
 * 描述： 系统文件转换器
 * <p>
 * 负责表单（{@link SysFileForm}）到实体（{@link SysFileEntity}）、
 * 实体到响应（{@link SysFileResponse}）之间的转换；
 *
 * @author xht
 * @see BasicConverter
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysFileConverter extends BasicConverter<SysFileEntity, SysFileForm, SysFileResponse> {

    /**
     * 构建预览初始化信息
     * <p>名称优先取元数据中的完整原始名称（入库名称超长会被截断）；地址为相对服务根路径，前端按自身接口前缀拼接</p>
     *
     * @param sysFileEntity 文件实体
     * @return 预览初始化信息
     */
    default SysFilePreviewResponse buildPreviewInfo(SysFileEntity sysFileEntity) {
        if (sysFileEntity == null) {
            return null;
        }
        SysFilePreviewResponse response = new SysFilePreviewResponse();
        response.setFileName(sysFileEntity.getFileStorageName());
        response.setFullOriginalFileName(Optional.ofNullable(sysFileEntity.getFileMetadata()).map(FileMetadata::getFullOriginalFilename).orElse(""));
        response.setFileSize(sysFileEntity.getFileSize());
        response.setFileSizeInfo(sysFileEntity.getFileSizeInfo());
        response.setFileSuffix(sysFileEntity.getFileSuffix());
        return response;
    }
}
