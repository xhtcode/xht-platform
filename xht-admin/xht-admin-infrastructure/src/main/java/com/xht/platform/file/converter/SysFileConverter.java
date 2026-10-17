package com.xht.platform.file.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.framework.utils.StringUtils;
import com.xht.platform.common.file.constant.SysFileConstant;
import com.xht.platform.file.domain.form.SysFileForm;
import com.xht.platform.file.domain.response.SysFileResponse;
import com.xht.platform.file.entity.SysFileEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.Objects;

/**
 * 描述： 系统文件转换器
 * <p>
 * 负责表单（{@link SysFileForm}）到实体（{@link SysFileEntity}）、
 * 实体到响应（{@link SysFileResponse}）之间的转换；
 * 转换后回填下载/预览/缩略图访问地址（{@link #fillUrls}）
 *
 * @author xht
 * @see BasicConverter
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysFileConverter extends BasicConverter<SysFileEntity, SysFileForm, SysFileResponse> {

    /**
     * 实体转响应后回填访问地址：下载/预览地址始终回填，缩略图地址仅在存在缩略图 key 时回填
     *
     * @param entity   文件实体
     * @param response 响应对象（MapStruct 映射目标）
     */
    @AfterMapping
    default void fillUrls(SysFileEntity entity, @MappingTarget SysFileResponse response) {
        if (Objects.isNull(response) || !StringUtils.hasText(response.getFileStorageKey())) {
            return;
        }
        response.setDownloadUrl(SysFileConstant.URL_DOWNLOAD_PREFIX + response.getFileStorageKey());
        response.setPreviewUrl(SysFileConstant.URL_PREVIEW_PREFIX + response.getFileStorageKey());
        if (StringUtils.hasText(response.getFileThumbKey())) {
            response.setThumbnailUrl(SysFileConstant.URL_THUMBNAIL_PREFIX + response.getFileStorageKey());
        }
    }

}
