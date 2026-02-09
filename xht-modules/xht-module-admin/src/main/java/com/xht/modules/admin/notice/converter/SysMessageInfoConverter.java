package com.xht.modules.admin.notice.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.converter.PageConverter;
import com.xht.modules.admin.notice.domain.response.SysMessageInfoResponse;
import com.xht.modules.admin.notice.domain.vo.MessagePageVo;
import com.xht.modules.admin.notice.entity.SysMessageEntity;
import com.xht.modules.admin.notice.entity.SysMessageInfoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * 描述 ： 系统管理-站内信收件人明细表 Converter
 *
 * @author xht
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysMessageInfoConverter extends PageConverter<SysMessageInfoEntity, SysMessageInfoResponse> {

    /**
     * 描述 ： 转换为站内信发送详情
     *
     * @param messageEntity 站内信
     * @param pageResponse  站内信收件人明细列表
     * @return 站内信发送详情
     */
    default MessagePageVo toMessageVo(SysMessageEntity messageEntity, Page<SysMessageInfoEntity> pageResponse) {
        MessagePageVo messagePageVo = new MessagePageVo();
        messagePageVo.setSenderName(messageEntity.getSenderName());
        messagePageVo.setMessageType(messageEntity.getMessageType());
        messagePageVo.setMessageTitle(messageEntity.getMessageTitle());
        messagePageVo.setMessageContent(messageEntity.getMessageContent());
        messagePageVo.setMessageExtend(messageEntity.getMessageExtend());
        messagePageVo.setCurrent(pageResponse.getCurrent());
        messagePageVo.setSize(pageResponse.getSize());
        messagePageVo.setPages(pageResponse.getPages());
        messagePageVo.setTotal(pageResponse.getTotal());
        messagePageVo.setRecords(toResponse(pageResponse.getRecords()));
        return messagePageVo;
    }
}
