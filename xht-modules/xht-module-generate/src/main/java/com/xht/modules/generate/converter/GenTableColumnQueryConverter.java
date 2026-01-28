package com.xht.modules.generate.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.modules.generate.domain.form.GenTableColumnQueryForm;
import com.xht.modules.generate.domain.response.GenTableColumnQueryResponse;
import com.xht.modules.generate.entity.GenTableColumnQueryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GenTableColumnQueryConverter extends BasicConverter<GenTableColumnQueryEntity, GenTableColumnQueryForm, GenTableColumnQueryResponse> {
}
