package com.xht.modules.admin.notice.dao.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.mapper.BaseMapperX;
import com.xht.modules.admin.notice.domain.query.SysNoticeQuery;
import com.xht.modules.admin.notice.domain.response.SysNoticeResponse;
import com.xht.modules.admin.notice.entity.SysNoticeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 系统管理-通知详情 的数据库操作Mapper
 *
 * @author admin
 */
@Mapper
public interface SysNoticeMapper extends BaseMapperX<SysNoticeEntity> {

    /**
     * 分页查询
     *
     * @param page        分页参数
     * @param noticeQuery 查询参数
     * @return 分页数据
     */
    Page<SysNoticeResponse> findPageList(@Param("page") Page<SysNoticeEntity> page, @Param("query") SysNoticeQuery noticeQuery);

}




