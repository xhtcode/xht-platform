package com.xht.modules.admin.notice.service.impl;

import com.xht.framework.core.domain.LabelValue;
import com.xht.modules.admin.notice.converter.SysNoticeTypeConverter;
import com.xht.modules.admin.notice.dao.SysNoticeTypeDao;
import com.xht.modules.admin.notice.domain.form.SysNoticeTypeForm;
import com.xht.modules.admin.notice.domain.query.SysNoticeTypeQuery;
import com.xht.modules.admin.notice.domain.response.SysNoticeTypeResponse;
import com.xht.modules.admin.notice.entity.SysNoticeTypeEntity;
import com.xht.modules.admin.notice.service.ISysNoticeTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统管理-通知类型 的数据库操作Service实现
 *
 * @author admin
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ISysNoticeTypeServiceImpl implements ISysNoticeTypeService {

    private final SysNoticeTypeDao sysNoticeTypeDao;

    private final SysNoticeTypeConverter sysNoticeTypeConverter;

    /**
     * 创建通知类型
     *
     * @param form 通知类型表单请求参数
     */
    @Override
    public void create(SysNoticeTypeForm form) {
        SysNoticeTypeEntity entity = sysNoticeTypeConverter.toEntity(form);
        sysNoticeTypeDao.save(entity);
    }

    /**
     * 根据ID删除通知类型
     *
     * @param id 通知类型ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeById(Long id) {
        sysNoticeTypeDao.removeById(id);
    }

    /**
     * 根据ID更新通知类型
     *
     * @param form 通知类型更新请求参数
     */
    @Override
    public void updateById(SysNoticeTypeForm form) {
        sysNoticeTypeDao.updateFormRequest(form);
    }

    /**
     * 根据ID查询通知类型
     *
     * @param id 通知类型ID
     * @return 通知类型信息
     */
    @Override
    public SysNoticeTypeResponse findById(Long id) {
        return sysNoticeTypeConverter.toResponse(sysNoticeTypeDao.findById(id));
    }

    /**
     * 查询所有通知类型
     * @param query 通知类型查询请求参数
     * @return 通知类型列表
     */
    @Override
    public List<SysNoticeTypeResponse> list(SysNoticeTypeQuery query) {
        return sysNoticeTypeConverter.toResponse(sysNoticeTypeDao.findList(query));
    }

    /**
     * 获取所有通知类型
     *
     * @return 获取所有通知类型
     */
    @Override
    public List<LabelValue<Long, String>> findEnableList() {
        List<SysNoticeTypeEntity> enableList = sysNoticeTypeDao.findEnableList();
        return enableList
                .stream()
                .map(entity -> LabelValue.of(entity.getId(), entity.getNoticeTypeName()))
                .toList();
    }

}




