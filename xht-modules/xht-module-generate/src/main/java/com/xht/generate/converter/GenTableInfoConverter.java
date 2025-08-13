package com.xht.generate.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.generate.domain.entity.GenTableInfoEntity;
import com.xht.generate.domain.request.GenTableInfoFormRequest;
import com.xht.generate.domain.response.GenTableInfoResponse;
import com.xht.generate.domain.vo.GenTableColumnVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 表信息转换器
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GenTableInfoConverter extends BasicConverter<GenTableInfoEntity, GenTableInfoFormRequest, GenTableInfoResponse> {

    /**
     * 将表信息实体对象转换为表列信息视图对象
     *
     * @param entity 表信息实体对象，非null
     * @return 转换后的表列信息视图对象，非null
     */
    GenTableColumnVo toVo(GenTableInfoEntity entity);

}
