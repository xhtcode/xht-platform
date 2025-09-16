package com.xht.generate.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.generate.domain.entity.GenTableColumnQueryEntity;
import com.xht.generate.domain.form.GenTableColumnQueryFormRequest;
import com.xht.generate.domain.response.GenTableColumnQueryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GenTableColumnQueryConverter extends BasicConverter<GenTableColumnQueryEntity, GenTableColumnQueryFormRequest, GenTableColumnQueryResponse> {
}
