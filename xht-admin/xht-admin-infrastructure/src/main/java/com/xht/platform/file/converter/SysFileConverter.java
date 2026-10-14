package com.xht.platform.file.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.platform.file.domain.form.SysFileForm;
import com.xht.platform.file.domain.response.SysFileResponse;
import com.xht.platform.file.entity.SysFileEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 描述： 系统文件转换器
 * <p>
 * 负责表单（{@link SysFileForm}）到实体（{@link SysFileEntity}）、
 * 实体到响应（{@link SysFileResponse}）之间的转换
 *
 * @author xht
 * @see BasicConverter
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysFileConverter extends BasicConverter<SysFileEntity, SysFileForm, SysFileResponse> {
}
