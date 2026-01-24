package com.xht.modules.admin.notice.dao.mapper;

import com.xht.framework.mybatis.mapper.BaseMapperX;
import com.xht.modules.admin.notice.entity.SysNoticeUserOperateEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统管理-用户操作记录(防重复统计) 的数据库操作Mapper
 *
 * @author admin
 */
@Mapper
public interface SysNoticeUserOperateMapper extends BaseMapperX<SysNoticeUserOperateEntity> {

}




