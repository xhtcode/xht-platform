package com.xht.system.modules.dict.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.system.modules.dict.common.enums.DictStatusEnums;
import com.xht.system.modules.dict.dao.SysDictDao;
import com.xht.system.modules.dict.dao.mapper.SysDictItemMapper;
import com.xht.system.modules.dict.dao.mapper.SysDictMapper;
import com.xht.system.modules.dict.domain.entity.SysDictEntity;
import com.xht.system.modules.dict.domain.entity.SysDictItemEntity;
import com.xht.system.modules.dict.domain.form.SysDictForm;
import com.xht.system.modules.dict.domain.query.SysDictQuery;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 系统字典管理
 *
 * @author xht
 **/
@Slf4j
@Repository
public class SysDictDaoImpl extends MapperRepositoryImpl<SysDictMapper, SysDictEntity> implements SysDictDao {

    @Resource
    private SysDictItemMapper sysDictItemMapper;

    /**
     * 修改系统字典
     *
     * @param form             系统字典修改参数
     * @param updateItemStatus 是否更新字典项状态
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRequest(SysDictForm form, boolean updateItemStatus) {
        LambdaUpdateWrapper<SysDictEntity> updateWrapper = new LambdaUpdateWrapper<>();
        //@formatter:off
        updateWrapper
                .set(condition(form.getDictCode()), SysDictEntity::getDictCode, form.getDictCode())
                .set(condition(form.getDictName()), SysDictEntity::getDictName, form.getDictName())
                .set(condition(form.getSortOrder()), SysDictEntity::getSortOrder, form.getSortOrder())
                .set(condition(form.getRemark()), SysDictEntity::getRemark, form.getRemark())
                .set(condition(form.getStatus()), SysDictEntity::getStatus, form.getStatus())
                .set(condition(form.getShowDisabled()), SysDictEntity::getShowDisabled, form.getShowDisabled())
                .eq(SysDictEntity::getId, form.getId());
        //@formatter:on
        if (updateItemStatus) {
            LambdaUpdateWrapper<SysDictItemEntity> itemEntityWrapper = new LambdaUpdateWrapper<>();
            itemEntityWrapper
                    .set(SysDictItemEntity::getStatus, form.getStatus())
                    .set(SysDictItemEntity::getDictCode, form.getDictCode())
                    .eq(SysDictItemEntity::getDictId, form.getId());
            sysDictItemMapper.update(itemEntityWrapper);
        }
        update(updateWrapper);
    }

    /**
     * 根据字典ID和字典项编码检查是否存在相同的字典项编码
     *
     * @param dictId   字典id
     * @param dictCode 字典code
     * @return 存在相同的字典项编码返回true，否则返回false
     */
    @Override
    public Boolean checkDictCode(Long dictId, String dictCode) {
        //@formatter:off
        LambdaQueryWrapper<SysDictEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(SysDictEntity::getDictCode, dictCode)
                .ne(Objects.nonNull(dictId), SysDictEntity::getId, dictId);
        //@formatter:on
        return SqlHelper.retBool(count(lambdaQueryWrapper));
    }

    /**
     * 查询系统字典列表
     *
     * @param query 系统字典查询参数
     * @return 系统字典列表
     */
    @Override
    public Page<SysDictEntity> findPageList(Page<SysDictEntity> page, SysDictQuery query) {
        LambdaQueryWrapper<SysDictEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.and(condition(query.getKeyWord()), wrapper -> wrapper
                .like(SysDictEntity::getDictCode, query.getKeyWord())
                .or()
                .like(SysDictEntity::getDictName, query.getKeyWord())
        );
        queryWrapper.like(condition(query.getDictCode()), SysDictEntity::getDictCode, query.getDictCode());
        queryWrapper.like(condition(query.getDictName()), SysDictEntity::getDictName, query.getDictName());
        queryWrapper.eq(condition(query.getStatus()), SysDictEntity::getStatus, query.getStatus());
        return page(page, queryWrapper);
    }

    /**
     * 查询所有字典项
     *
     * @return 字典项列表
     */
    @Override
    public List<SysDictEntity> findAllByStatus() {
        LambdaQueryWrapper<SysDictEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(
                SysDictEntity::getId,
                SysDictEntity::getDictCode,
                SysDictEntity::getDictCode
        );
        queryWrapper.eq(SysDictEntity::getStatus, DictStatusEnums.ENABLE);
        queryWrapper.orderByAsc(SysDictEntity::getSortOrder);
        return list(queryWrapper);
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<SysDictEntity, ?> getFieldId() {
        return SysDictEntity::getId;
    }
}
