package com.xht.modules.admin.audit.converter;

import com.xht.api.system.domain.response.BLogResponse;
import com.xht.framework.core.support.blog.dto.BLogDTO;
import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.modules.admin.audit.entity.BLogEntity;
import com.xht.api.system.domain.form.BLogForm;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 系统日志
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BLogConverter extends BasicConverter<BLogEntity, BLogForm, BLogResponse> {

    /**
     * 将LogDTO对象转换为 BLogEntity 对象
     *
     * @param bLogDTO 日志数据传输对象，包含需要转换的日志信息
     * @return 转换后的系统日志实体对象
     */
    BLogEntity toEntity(BLogDTO bLogDTO);
}
