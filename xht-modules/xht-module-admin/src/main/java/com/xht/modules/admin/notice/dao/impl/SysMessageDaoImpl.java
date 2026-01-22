package com.xht.modules.admin.notice.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.modules.admin.notice.dao.SysMessageDao;
import com.xht.modules.admin.notice.dao.mapper.SysMessageMapper;
import com.xht.modules.admin.notice.domain.query.SysMessageQuery;
import com.xht.modules.admin.notice.entity.SysMessageEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 描述 ： 系统管理-站内信主表 Dao
 *
 * @author xht
 **/
@Slf4j
@Repository
public class SysMessageDaoImpl extends MapperRepositoryImpl<SysMessageMapper, SysMessageEntity> implements SysMessageDao {

    /**
     * 管理员分页查询站内信
     *
     * @param page  分页参数
     * @param query 站内信查询参数
     */
    @Override
    public Page<SysMessageEntity> findPageList(Page<SysMessageEntity> page, SysMessageQuery query) {
        LambdaQueryWrapper<SysMessageEntity> queryWrapper = new LambdaQueryWrapper<>();
        return page(page, queryWrapper);
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<SysMessageEntity, ?> getFieldId() {
        return SysMessageEntity::getId;
    }

}
