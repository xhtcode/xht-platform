package com.xht.platform.table.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.platform.table.domain.form.GenTableColumnQueryForm;
import com.xht.platform.table.entity.GenTableColumnQueryEntity;
import com.xht.platform.table.domain.response.GenTableColumnQueryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GenTableColumnQueryConverter extends BasicConverter<GenTableColumnQueryEntity, GenTableColumnQueryForm, GenTableColumnQueryResponse> {

}
