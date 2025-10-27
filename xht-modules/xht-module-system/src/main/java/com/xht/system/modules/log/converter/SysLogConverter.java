package com.xht.system.modules.log.converter;

import com.xht.framework.log.domain.dto.LogDTO;
import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.system.modules.log.domian.entity.SysLogEntity;
import com.xht.system.modules.log.domian.request.SysLogBasicForm;
import com.xht.system.modules.log.domian.response.SysLogResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 系统日志
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysLogConverter extends BasicConverter<SysLogEntity, SysLogBasicForm, SysLogResponse> {

    /**
     * 将LogDTO对象转换为SysLogEntity对象
     *
     * @param logDTO 日志数据传输对象，包含需要转换的日志信息
     * @return 转换后的系统日志实体对象
     */
    SysLogEntity toEntity(LogDTO logDTO);
}
