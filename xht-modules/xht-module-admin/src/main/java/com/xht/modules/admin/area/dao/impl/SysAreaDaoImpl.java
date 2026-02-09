package com.xht.modules.admin.area.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.modules.admin.area.dao.SysAreaDao;
import com.xht.modules.admin.area.dao.mapper.SysAreaMapper;
import com.xht.modules.admin.area.domain.form.SysAreaForm;
import com.xht.modules.admin.area.domain.query.SysAreaQuery;
import com.xht.modules.admin.area.entity.SysAreaEntity;
import com.xht.modules.admin.area.enums.AreaHasChildEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * 系统管理 - 行政区划
 *
 * @author xht
 **/
@Slf4j
@Repository
public class SysAreaDaoImpl extends MapperRepositoryImpl<SysAreaMapper, SysAreaEntity> implements SysAreaDao {

    /**
     * 修改节点 hasChild属性
     *
     * @param id       主键
     * @param hasChild 是否有子节点
     */
    @Override
    public void updateHasChild(Long id, AreaHasChildEnums hasChild) {
        LambdaUpdateWrapper<SysAreaEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(SysAreaEntity::getHasChild, Objects.requireNonNullElse(hasChild, AreaHasChildEnums.NO_CHILD));
        updateWrapper.eq(SysAreaEntity::getId, id);
        update(updateWrapper);
    }

    /**
     * 根据主键`areaCode`更新系统管理-行政区划
     *
     * @param form 系统管理-行政区划表单请求参数
     */
    @Override
    public void updateFormRequest(SysAreaForm form) {
        LambdaUpdateWrapper<SysAreaEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(condition(form.getParentId()), SysAreaEntity::getParentId, form.getParentId());
        updateWrapper.set(condition(form.getAreaCode()), SysAreaEntity::getAreaCode, form.getAreaCode());
        updateWrapper.set(condition(form.getAreaName()), SysAreaEntity::getAreaName, form.getAreaName());
        updateWrapper.set(condition(form.getAreaPostCode()), SysAreaEntity::getAreaPostCode, form.getAreaPostCode());
        updateWrapper.set(condition(form.getAreaLongitude()), SysAreaEntity::getAreaLongitude, form.getAreaLongitude());
        updateWrapper.set(condition(form.getAreaLatitude()), SysAreaEntity::getAreaLatitude, form.getAreaLatitude());
        updateWrapper.set(condition(form.getAreaSort()), SysAreaEntity::getAreaSort, form.getAreaSort());
        updateWrapper.eq(SysAreaEntity::getId, form.getId());
        update(updateWrapper);
    }

    /**
     * 校验区划下是否有子区划
     *
     * @param parentId 上级区划编码
     * @return true:有子区划
     */
    @Override
    public boolean existsChild(Long parentId) {
        LambdaQueryWrapper<SysAreaEntity> updateWrapper = new LambdaQueryWrapper<>();
        updateWrapper.eq(SysAreaEntity::getParentId, parentId);
        return exists(updateWrapper);
    }

    /**
     * 校验区划下是否有子区划
     *
     * @param areaCode 区划编码
     * @param id       区划ID
     * @return true:有子区划
     */
    @Override
    public boolean existsAreaCode(String areaCode, Long id) {
        LambdaQueryWrapper<SysAreaEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysAreaEntity::getAreaCode, areaCode);
        queryWrapper.ne(condition(id), SysAreaEntity::getId, id);
        return exists(queryWrapper);
    }

    /**
     * 根据上级区划编码查询子区划
     *
     * @param parentId 上级区划编码
     * @return 子区划列表
     */
    @Override
    public List<SysAreaEntity> listByParentId(Long parentId) {
        LambdaUpdateWrapper<SysAreaEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysAreaEntity::getParentId, parentId);
        wrapper.orderByAsc(SysAreaEntity::getAreaCode);
        return baseMapper.selectList(wrapper);
    }


    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<SysAreaEntity, ?> getFieldId() {
        return SysAreaEntity::getId;
    }

}
