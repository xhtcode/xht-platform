package com.xht.modules.admin.notice.dao.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.mapper.BaseMapperX;
import com.xht.modules.admin.notice.domain.query.SysMessageInfoQuery;
import com.xht.modules.admin.notice.domain.vo.MessageInfoVo;
import com.xht.modules.admin.notice.entity.SysMessageInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 系统管理-站内信收件人明细表
 *
 * @author xht
 */
@Mapper
public interface SysMessageInfoMapper extends BaseMapperX<SysMessageInfoEntity> {

    /**
     * 查询站内信详情
     *
     * @param messageId 站内信ID
     * @param userId    用户ID
     * @return 站内信详情信息
     */
    MessageInfoVo findInfoByMessageId(@Param("messageId") Long messageId, @Param("userId") Long userId);

    /**
     * 分页查询我接收的站内信
     *
     * @param page  分页参数
     * @param query 查询参数
     * @return  站内信分页
     */
    Page<MessageInfoVo> findMyMessagePageList(@Param("page") Page<?> page, @Param("query") SysMessageInfoQuery query);

}




