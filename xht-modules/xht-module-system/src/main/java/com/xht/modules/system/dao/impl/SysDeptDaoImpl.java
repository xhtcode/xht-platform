package com.xht.modules.system.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.modules.common.enums.DeptStatusEnums;
import com.xht.modules.system.dao.SysDeptDao;
import com.xht.modules.system.dao.mapper.SysDeptMapper;
import com.xht.modules.system.domain.entity.SysDeptEntity;
import com.xht.modules.system.domain.query.SysDeptTreeQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 部门管理Dao
 *
 * @author xht
 */
@Slf4j
@Repository
public class SysDeptDaoImpl extends MapperRepositoryImpl<SysDeptMapper, SysDeptEntity> implements SysDeptDao {

    /**
     * 保存部门初始化数据
     *
     * @param entity 部门实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveDeptInitPost(SysDeptEntity entity) {
        save(entity);
    }


    /**
     * 更新部门信息
     *
     * @param form 部门更新请求
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFormRequest(SysDeptEntity form) {
        LambdaUpdateWrapper<SysDeptEntity> updateWrapper = new LambdaUpdateWrapper<>();
        // @formatter:off
        updateWrapper
                .set(condition(form.getDeptName()), SysDeptEntity::getDeptName, form.getDeptName())
                .set(condition(form.getDeptCode()), SysDeptEntity::getDeptCode, form.getDeptCode())
                .set(condition(form.getDeptName()), SysDeptEntity::getDeptName, form.getDeptName())
                .set(condition(form.getParentId()), SysDeptEntity::getParentId, form.getParentId())
                .set(condition(form.getDeptStatus()), SysDeptEntity::getDeptStatus, form.getDeptStatus())
                .set(condition(form.getDeptSort()), SysDeptEntity::getDeptSort, form.getDeptSort())
                .set(condition(form.getDeptLevel()), SysDeptEntity::getDeptLevel, form.getDeptLevel())
                .set(condition(form.getAncestors()), SysDeptEntity::getAncestors, form.getAncestors())
                .set(condition(form.getPhone()), SysDeptEntity::getPhone, form.getPhone())
                .set(condition(form.getEmail()), SysDeptEntity::getEmail, form.getEmail())
                .set(condition(form.getRemark()), SysDeptEntity::getRemark, form.getRemark())
                .eq(SysDeptEntity::getId, form.getId());
        // @formatter:on
        update(updateWrapper);
    }

    /**
     * 更新部门状态
     *
     * @param id     部门id
     * @param status 部门状态
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Long id, DeptStatusEnums status) {
        LambdaUpdateWrapper<SysDeptEntity> updateWrapper = new LambdaUpdateWrapper<>();
        // @formatter:off
        updateWrapper
                .set(SysDeptEntity::getDeptStatus, status.getValue())
                .eq(SysDeptEntity::getId, id);
        // @formatter:on
        update(updateWrapper);
    }

    /**
     * 检查部门编码是否唯一
     *
     * @param deptId   部门id
     * @param deptCode 部门编码
     * @return true：唯一；false：不唯一
     */
    @Override
    public Boolean checkDeptCodeUnique(Long deptId, String deptCode) {
        //@formatter:off
        LambdaQueryWrapper<SysDeptEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(SysDeptEntity::getDeptCode, deptCode)
                .ne(Objects.nonNull(deptId), SysDeptEntity::getId, deptId);
        //@formatter:on
        return SqlHelper.retBool(count(lambdaQueryWrapper));
    }

    /**
     * 获取上级父部门信息
     *
     * @param parentId 父部门id
     * @return 父部门
     */
    @Override
    public SysDeptEntity getDefaultParentDeptByParentId(Long parentId) {
        if (Objects.equals(parentId, 0L)) {
            SysDeptEntity sysDeptEntity = new SysDeptEntity();
            sysDeptEntity.setId(0L);
            sysDeptEntity.setParentId(0L);
            sysDeptEntity.setDeptLevel(0);
            sysDeptEntity.setDeptStatus(DeptStatusEnums.NORMAL);
            sysDeptEntity.setDeptSort(0);
            sysDeptEntity.setAncestors("0");
            return sysDeptEntity;
        }
        // @formatter:off
        LambdaQueryWrapper<SysDeptEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(
                SysDeptEntity::getId,
                SysDeptEntity::getParentId,
                SysDeptEntity::getDeptLevel,
                SysDeptEntity::getDeptStatus,
                SysDeptEntity::getDeptSort,
                SysDeptEntity::getAncestors);
        lambdaQueryWrapper.eq(SysDeptEntity::getId, parentId);
        // @formatter:on
        return getOne(lambdaQueryWrapper);
    }


    /**
     * 查询部门列表信息
     *
     * @param query 查询请求参数
     * @return 部门列表
     */
    @Override
    public List<SysDeptEntity> queryListRequest(SysDeptTreeQuery query) {
        // @formatter:off
        LambdaQueryWrapper<SysDeptEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.and(
                        condition(query.getKeyWord()), wrapper ->
                                wrapper
                                        .or()
                                        .like(SysDeptEntity::getDeptName, query.getKeyWord())
                                        .or()
                                        .like(SysDeptEntity::getDeptCode, query.getKeyWord())
                                        .or()
                                        .like(SysDeptEntity::getPhone, query.getKeyWord())
                                        .or()
                                        .like(SysDeptEntity::getEmail, query.getKeyWord())
                )
                .eq(condition(query.getParentId()), SysDeptEntity::getParentId, query.getParentId())
                .eq(condition(query.getDeptStatus()), SysDeptEntity::getDeptStatus, query.getDeptStatus())
                .like(condition(query.getDeptCode()), SysDeptEntity::getDeptCode, query.getDeptCode())
                .like(condition(query.getDeptName()), SysDeptEntity::getDeptName, query.getDeptName())
                .like(condition(query.getPhone()), SysDeptEntity::getPhone, query.getPhone())
                .like(condition(query.getEmail()), SysDeptEntity::getPhone, query.getEmail())
        ;
        // @formatter:on
        return list(queryWrapper);
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<SysDeptEntity, ?> getFieldId() {
        return SysDeptEntity::getId;
    }
}