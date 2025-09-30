package com.xht.system.modules.dict.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.system.modules.dict.dao.SysDictItemDao;
import com.xht.system.modules.dict.dao.mapper.SysDictItemMapper;
import com.xht.system.modules.dict.domain.entity.SysDictItemEntity;
import com.xht.system.modules.dict.domain.request.SysDictItemForm;
import com.xht.system.modules.dict.domain.request.SysDictItemQuery;
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
public class SysDictItemDaoImpl extends MapperRepositoryImpl<SysDictItemMapper, SysDictItemEntity> implements SysDictItemDao {

    /**
     * 根据更新请求更新指定ID的字典项实体
     *
     * @param form 更新请求
     * @return 更新结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateFormRequest(SysDictItemForm form) {
        // @formatter:off
        LambdaUpdateWrapper<SysDictItemEntity> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper
                .set(condition(form.getDictId()), SysDictItemEntity::getDictId, form.getDictId())
                .set(condition(form.getItemLabel()), SysDictItemEntity::getItemLabel, form.getItemLabel())
                .set(condition(form.getItemValue()), SysDictItemEntity::getItemValue, form.getItemValue())
                .set(condition(form.getItemColor()), SysDictItemEntity::getItemColor, form.getItemColor())
                .set(condition(form.getSortOrder()), SysDictItemEntity::getSortOrder, form.getSortOrder())
                .set(condition(form.getRemark()), SysDictItemEntity::getRemark, form.getRemark())
                .set(condition(form.getStatus()), SysDictItemEntity::getStatus, form.getStatus());
        lambdaUpdateWrapper.eq(SysDictItemEntity::getId, form.getId());
        // @formatter:on
        return update(lambdaUpdateWrapper);
    }


    /**
     * 检查字典项值是否存在
     *
     * @param dictId    字典ID
     * @param itemValue 字典项值
     * @return 存在相同的字典项编码返回true，否则返回false
     */
    @Override
    public Boolean checkDictValue(Long id, Long dictId, String itemValue) {
        LambdaQueryWrapper<SysDictItemEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysDictItemEntity::getItemValue, itemValue)
                .eq(SysDictItemEntity::getDictId, dictId)
                .ne(Objects.nonNull(id), SysDictItemEntity::getId, id)
        ;
        return SqlHelper.retBool(count(lambdaQueryWrapper));
    }

    /**
     * 分页查询字典项列表
     *
     * @param page         分页信息
     * @param query 字典项查询请求参数
     * @return 分页字典项列表
     */
    @Override
    public Page<SysDictItemEntity> queryPageRequest(Page<SysDictItemEntity> page, SysDictItemQuery query) {
        LambdaQueryWrapper<SysDictItemEntity> queryWrapper = Wrappers.lambdaQuery();
        // @formatter:off
        queryWrapper
                .and(condition(query.getKeyWord()), wrapper -> wrapper
                        .like(SysDictItemEntity::getDictCode, query.getKeyWord())
                        .or()
                        .like(SysDictItemEntity::getItemLabel, query.getKeyWord())
                        .or()
                        .like(SysDictItemEntity::getItemValue, query.getKeyWord())
                )
                .eq(condition(query.getDictId()), SysDictItemEntity::getDictId, query.getDictId())
                .like(condition(query.getDictCode()), SysDictItemEntity::getDictCode, query.getDictCode())
                .like(condition(query.getItemLabel()), SysDictItemEntity::getItemLabel, query.getItemLabel())
                .like(condition(query.getItemValue()), SysDictItemEntity::getItemValue, query.getItemValue())
                .eq(condition(query.getStatus()), SysDictItemEntity::getStatus, query.getStatus());
        // @formatter:on
        return page(page, queryWrapper);
    }

    /**
     * 根据字典id查询字典项列表
     *
     * @param dictId 字典id
     * @return 字典项列表
     */
    @Override
    public List<SysDictItemEntity> selectByDictId(Long dictId) {
        return findList(SysDictItemEntity::getDictId, dictId);
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<SysDictItemEntity, ?> getFieldId() {
        return SysDictItemEntity::getId;
    }
}
