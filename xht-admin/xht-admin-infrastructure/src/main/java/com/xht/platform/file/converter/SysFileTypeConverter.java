package com.xht.platform.file.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.platform.file.domain.form.SysFileTypeForm;
import com.xht.platform.file.domain.response.SysFileTypeResponse;
import com.xht.platform.file.entity.SysFileTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 描述： 文件预览类型配置转换器
 * <p>
 * 负责表单（{@link SysFileTypeForm}）到实体（{@link SysFileTypeEntity}）、
 * 实体到响应（{@link SysFileTypeResponse}）之间的转换
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysFileTypeConverter extends BasicConverter<SysFileTypeEntity, SysFileTypeForm, SysFileTypeResponse> {

}
