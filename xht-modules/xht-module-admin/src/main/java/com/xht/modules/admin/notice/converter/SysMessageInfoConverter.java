package com.xht.modules.admin.notice.converter;

import com.xht.framework.mybatis.converter.BasicConverter;
import com.xht.modules.admin.notice.domain.form.SysMessageInfoForm;
import com.xht.modules.admin.notice.domain.response.SysMessageInfoResponse;
import com.xht.modules.admin.notice.entity.SysMessageInfoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 描述 ： 系统管理-站内信收件人明细表
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysMessageInfoConverter extends BasicConverter<SysMessageInfoEntity, SysMessageInfoForm, SysMessageInfoResponse> {
}
