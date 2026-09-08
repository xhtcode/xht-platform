package com.xht.workflow.holiday.service;

import com.xht.framework.common.domain.response.PageResponse;
import com.xht.workflow.holiday.domain.form.ItemHolidayForm;
import com.xht.workflow.holiday.domain.query.ItemHolidayPageQuery;
import com.xht.workflow.holiday.domain.response.ItemHolidayResponse;

/**
 * 描述： 请假单服务类
 *
 * @author xht
 **/
public interface IItemHolidayService {

    /**
     * 创建请假单
     *
     * @param form 请假单信息
     */
    void create(ItemHolidayForm form);

    /**
     * 删除请假单
     *
     * @param id 请假单ID
     */
    void removeById(Long id);

    /**
     * 修改请假单
     *
     * @param id   请假单ID
     * @param form 请假单信息
     */
    void updateById(Long id, ItemHolidayForm form);

    /**
     * 获取请假单详情
     *
     * @param id 请假单ID
     * @return 请假单详情
     */
    ItemHolidayResponse findById(Long id);

    /**
     * 分页查询请假单
     *
     * @param query 请假单查询参数
     * @return 请假单分页信息
     */
    PageResponse<ItemHolidayResponse> findPageList(ItemHolidayPageQuery query);

}
