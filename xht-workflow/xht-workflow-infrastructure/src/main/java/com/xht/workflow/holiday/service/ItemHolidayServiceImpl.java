package com.xht.workflow.holiday.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.code.BusinessErrorCode;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.framework.utils.ThrowUtils;
import com.xht.workflow.holiday.converter.ItemHolidayConverter;
import com.xht.workflow.holiday.dao.ItemHolidayDao;
import com.xht.workflow.holiday.domain.form.ItemHolidayForm;
import com.xht.workflow.holiday.domain.query.ItemHolidayPageQuery;
import com.xht.workflow.holiday.domain.response.ItemHolidayResponse;
import com.xht.workflow.holiday.entity.ItemHolidayEntity;
import com.xht.workflow.process.dao.FlowBusinessDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 描述： 请假单服务实现类
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class ItemHolidayServiceImpl implements IItemHolidayService {

    private final ItemHolidayDao itemHolidayDao;

    private final FlowBusinessDao flowBusinessDao;

    private final ItemHolidayConverter itemHolidayConverter;

    /**
     * 创建请假单
     *
     * @param form 请假单信息
     */
    @Override
    public void create(ItemHolidayForm form) {
        checkBusiness(form.getBusinessId());
        checkTimeRange(form.getHolidayStartTime(), form.getHolidayEndTime());
        ItemHolidayEntity entity = itemHolidayConverter.toEntity(form);
        itemHolidayDao.saveTransactional(entity);
    }

    /**
     * 删除请假单
     *
     * @param id 请假单ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeById(Long id) {
        itemHolidayDao.removeById(id);
    }

    /**
     * 修改请假单
     *
     * @param id   请假单ID
     * @param form 请假单信息
     */
    @Override
    public void updateById(Long id, ItemHolidayForm form) {
        ThrowUtils.notNull(id);
        itemHolidayDao.findOptionalById(id)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.DATA_NOT_EXIST, "请假单不存在"));
        if (Objects.nonNull(form.getHolidayStartTime()) && Objects.nonNull(form.getHolidayEndTime())) {
            checkTimeRange(form.getHolidayStartTime(), form.getHolidayEndTime());
        }
        itemHolidayDao.updateHolidayRequest(id, form);
    }

    /**
     * 校验流程业务是否存在
     *
     * @param businessId 业务ID
     */
    private void checkBusiness(Long businessId) {
        ThrowUtils.throwIf(!flowBusinessDao.existsById(businessId),
                BusinessErrorCode.DATA_NOT_EXIST, "流程业务不存在");
    }

    /**
     * 校验请假开始时间不晚于结束时间
     *
     * @param startTime 请假开始时间
     * @param endTime   请假结束时间
     */
    private void checkTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        ThrowUtils.throwIf(endTime.isBefore(startTime),
                BusinessErrorCode.PARAM_ERROR, "请假结束时间不能早于开始时间");
    }

    /**
     * 获取请假单详情
     *
     * @param id 请假单ID
     * @return 请假单详情
     */
    @Override
    public ItemHolidayResponse findById(Long id) {
        return itemHolidayConverter.toResponse(itemHolidayDao.findOptionalById(id)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.DATA_NOT_EXIST)));
    }

    /**
     * 分页查询请假单
     *
     * @param query 请假单查询参数
     * @return 请假单分页信息
     */
    @Override
    public PageResponse<ItemHolidayResponse> findPageList(ItemHolidayPageQuery query) {
        Page<ItemHolidayEntity> page = itemHolidayDao.findPageList(PageTool.getPage(query), query);
        return itemHolidayConverter.toResponse(page);
    }
}
