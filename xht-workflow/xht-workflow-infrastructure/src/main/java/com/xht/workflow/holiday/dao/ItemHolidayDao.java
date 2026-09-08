package com.xht.workflow.holiday.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.workflow.holiday.domain.form.ItemHolidayForm;
import com.xht.workflow.holiday.domain.query.ItemHolidayPageQuery;
import com.xht.workflow.holiday.entity.ItemHolidayEntity;

/**
 * 流程事项-请假单
 *
 * @author xht
 */
public interface ItemHolidayDao extends MapperRepository<ItemHolidayEntity> {

    /**
     * 更新请假单信息
     *
     * @param id   ID
     * @param form 请假单参数
     */
    void updateHolidayRequest(Long id, ItemHolidayForm form);

    /**
     * 分页查询请假单
     *
     * @param page  分页信息
     * @param query 查询参数
     * @return 分页数据
     */
    Page<ItemHolidayEntity> findPageList(Page<ItemHolidayEntity> page, ItemHolidayPageQuery query);

}
