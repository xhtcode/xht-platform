package com.xht.modules.admin.notice.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.modules.admin.notice.domain.form.SysNoticePermissionForm;
import com.xht.modules.admin.notice.domain.response.SysNoticePermissionResponse;
import com.xht.modules.admin.notice.entity.SysNoticePermissionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述 ： 系统管理-站内信收件人明细表 Converter
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysNoticePermissionConverter extends BasicConverter<SysNoticePermissionEntity, SysNoticePermissionForm, SysNoticePermissionResponse> {
    /**
     * 将创建请求对象转换为实体对象
     *
     * @param form 创建请求对象，非null
     * @return 转换后的实体对象，非null
     */
    default SysNoticePermissionEntity toEntity(SysNoticePermissionForm form, Long noticeId) {
        SysNoticePermissionEntity entity = toEntity(form);
        entity.setId(noticeId);
        return entity;
    }

    /**
     * 将请求对象列表转换为实体对象列表
     *
     * @param permissionForms 请求对象列表，可为null或空
     * @return 转换后的实体对象列表，非null（空列表而非null）
     */
    default List<SysNoticePermissionEntity> toEntity(List<SysNoticePermissionForm> permissionForms, Long noticeId) {
        if (CollectionUtils.isEmpty(permissionForms)) {
            return List.of(); // 使用Java 9+的不可变空列表
        }
        return permissionForms.stream()
                .map(item -> {
                    return toEntity(item, noticeId);
                })
                .collect(Collectors.toList());
    }

}