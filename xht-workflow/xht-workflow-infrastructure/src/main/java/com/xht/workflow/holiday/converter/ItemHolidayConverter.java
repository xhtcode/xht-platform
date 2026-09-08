package com.xht.workflow.holiday.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.workflow.holiday.domain.form.ItemHolidayForm;
import com.xht.workflow.holiday.domain.response.ItemHolidayResponse;
import com.xht.workflow.holiday.entity.ItemHolidayEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 描述：流程事项-请假单
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ItemHolidayConverter extends BasicConverter<ItemHolidayEntity, ItemHolidayForm, ItemHolidayResponse> {

}
