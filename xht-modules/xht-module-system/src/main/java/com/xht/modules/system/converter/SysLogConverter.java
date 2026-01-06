package com.xht.modules.system.converter;

import com.xht.framework.core.support.blog.dto.BLogDTO;
import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.modules.system.domain.entity.SysLogEntity;
import com.xht.modules.system.domain.request.SysLogForm;
import com.xht.modules.system.domain.response.SysLogResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 系统日志
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysLogConverter extends BasicConverter<SysLogEntity, SysLogForm, SysLogResponse> {

    /**
     * 将LogDTO对象转换为SysLogEntity对象
     *
     * @param bLogDTO 日志数据传输对象，包含需要转换的日志信息
     * @return 转换后的系统日志实体对象
     */
    SysLogEntity toEntity(BLogDTO bLogDTO);
}
