package com.xht.workflow.holiday.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.workflow.holiday.dao.ItemHolidayDao;
import com.xht.workflow.holiday.dao.mapper.ItemHolidayMapper;
import com.xht.workflow.holiday.domain.form.ItemHolidayForm;
import com.xht.workflow.holiday.domain.query.ItemHolidayPageQuery;
import com.xht.workflow.holiday.entity.ItemHolidayEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 流程事项-请假单
 *
 * @author xht
 */
@Slf4j
@Repository
public class ItemHolidayDaoImpl extends MapperRepositoryImpl<ItemHolidayMapper, ItemHolidayEntity> implements ItemHolidayDao {

    @Override
    protected SFunction<ItemHolidayEntity, ?> getFieldId() {
        return ItemHolidayEntity::getId;
    }

    /**
     * 更新请假单信息
     *
     * @param id   ID
     * @param form 请假单参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateHolidayRequest(Long id, ItemHolidayForm form) {
        LambdaUpdateWrapper<ItemHolidayEntity> updateWrapper = new LambdaUpdateWrapper<>();
        //@formatter:off
        updateWrapper
                .set(condition(form.getHolidayType()), ItemHolidayEntity::getHolidayType, form.getHolidayType())
                .set(condition(form.getHolidayStartTime()), ItemHolidayEntity::getHolidayStartTime, form.getHolidayStartTime())
                .set(condition(form.getHolidayEndTime()), ItemHolidayEntity::getHolidayEndTime, form.getHolidayEndTime())
                .set(condition(form.getHolidayDays()), ItemHolidayEntity::getHolidayDays, form.getHolidayDays())
                .set(condition(form.getHolidayReason()), ItemHolidayEntity::getHolidayReason, form.getHolidayReason())
                .set(condition(form.getHolidayRemark()), ItemHolidayEntity::getHolidayRemark, form.getHolidayRemark())
                .eq(ItemHolidayEntity::getId, id);
        //@formatter:on
        update(updateWrapper);
    }

    /**
     * 分页查询请假单
     *
     * @param page  分页信息
     * @param query 查询参数
     * @return 分页数据
     */
    @Override
    public Page<ItemHolidayEntity> findPageList(Page<ItemHolidayEntity> page, ItemHolidayPageQuery query) {
        LambdaQueryWrapper<ItemHolidayEntity> queryWrapper = new LambdaQueryWrapper<>();
        if (query.isQuick()) {
            //@formatter:off
            queryWrapper.and(
                    condition(query.getKeyWord()), wrapper -> wrapper.or()
                            .like(ItemHolidayEntity::getHolidayReason, query.getKeyWord())
                            .or()
                            .like(ItemHolidayEntity::getHolidayRemark, query.getKeyWord())
            );
            //@formatter:on
        } else {
            //@formatter:off
            queryWrapper
                    .eq(condition(query.getBusinessId()), ItemHolidayEntity::getBusinessId, query.getBusinessId())
                    .eq(condition(query.getHolidayType()), ItemHolidayEntity::getHolidayType, query.getHolidayType())
                    .like(condition(query.getKeyWord()), ItemHolidayEntity::getHolidayReason, query.getKeyWord())
                    .like(condition(query.getKeyWord()), ItemHolidayEntity::getHolidayRemark, query.getKeyWord());
            //@formatter:on
        }
        return page(page, queryWrapper);
    }

}
