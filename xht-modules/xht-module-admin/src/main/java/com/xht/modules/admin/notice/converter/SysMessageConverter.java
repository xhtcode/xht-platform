package com.xht.modules.admin.notice.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.modules.admin.notice.domain.form.SysMessageForm;
import com.xht.modules.admin.notice.domain.response.SysMessageResponse;
import com.xht.modules.admin.notice.entity.SysMessageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 描述 ： 系统管理-站内信收件人明细表
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysMessageConverter extends BasicConverter<SysMessageEntity, SysMessageForm, SysMessageResponse> {

}
