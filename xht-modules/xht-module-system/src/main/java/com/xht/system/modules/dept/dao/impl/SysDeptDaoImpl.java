package com.xht.system.modules.dept.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.core.utils.spring.SpringContextUtils;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.system.event.SysDeptInitPostEvent;
import com.xht.system.modules.dept.common.enums.DeptStatusEnums;
import com.xht.system.modules.dept.dao.SysDeptDao;
import com.xht.system.modules.dept.dao.mapper.SysDeptMapper;
import com.xht.system.modules.dept.domain.entity.SysDeptEntity;
import com.xht.system.modules.dept.domain.request.SysDeptQueryTreeRequest;
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
     * @return true：成功；false：失败
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveDeptInitPost(SysDeptEntity entity) {
        boolean save = save(entity);
        if (save) {
            SpringContextUtils.publishEvent(new SysDeptInitPostEvent(entity.getId(), entity.getLeaderUserId()));
        }
        return save;
    }


    /**
     * 更新部门信息
     *
     * @param formRequest     部门更新请求
     * @param oldLeaderUserId 旧的主管用户id
     * @return true：成功；false：失败
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateFormRequest(SysDeptEntity formRequest, Long oldLeaderUserId) {
        LambdaUpdateWrapper<SysDeptEntity> updateWrapper = new LambdaUpdateWrapper<>();
        // @formatter:off
        updateWrapper.set(SysDeptEntity::getDeptName, formRequest.getDeptName())
                .set(SysDeptEntity::getDeptCode, formRequest.getDeptCode())
                .set(SysDeptEntity::getDeptName, formRequest.getDeptName())
                .set(SysDeptEntity::getParentId, formRequest.getParentId())
                .set(SysDeptEntity::getDeptStatus, formRequest.getDeptStatus())
                .set(SysDeptEntity::getDeptSort, formRequest.getDeptSort())
                .set(SysDeptEntity::getDeptLevel, formRequest.getDeptLevel())
                .set(SysDeptEntity::getAncestors, formRequest.getAncestors())
                .set(SysDeptEntity::getLeaderUserId, formRequest.getLeaderUserId())
                .set(SysDeptEntity::getLeaderPostId, formRequest.getLeaderPostId())
                .set(SysDeptEntity::getLeaderName, formRequest.getLeaderName())
                .set(SysDeptEntity::getPhone, formRequest.getPhone())
                .set(SysDeptEntity::getEmail, formRequest.getEmail())
                .set(SysDeptEntity::getRemark, formRequest.getRemark())
                .eq(SysDeptEntity::getId, formRequest.getId());
        // @formatter:on
        return update(updateWrapper);
    }

    /**
     * 更新部门状态
     *
     * @param id     部门id
     * @param status 部门状态
     * @return true：成功；false：失败
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateStatus(Long id, DeptStatusEnums status) {
        LambdaUpdateWrapper<SysDeptEntity> updateWrapper = new LambdaUpdateWrapper<>();
        // @formatter:off
        updateWrapper
                .set(SysDeptEntity::getDeptStatus, status.getValue())
                .eq(SysDeptEntity::getId, id);
        // @formatter:on
        return update(updateWrapper);
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
     * @param queryRequest 查询请求参数
     * @return 部门列表
     */
    @Override
    public List<SysDeptEntity> queryListRequest(SysDeptQueryTreeRequest queryRequest) {
        // @formatter:off
        LambdaQueryWrapper<SysDeptEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.and(
                        StringUtils.hasText(queryRequest.getKeyWord()), wrapper ->
                                wrapper
                                        .or()
                                        .like(SysDeptEntity::getDeptName, queryRequest.getKeyWord())
                                        .or()
                                        .like(SysDeptEntity::getDeptCode, queryRequest.getKeyWord())
                                        .or()
                                        .like(SysDeptEntity::getPhone, queryRequest.getKeyWord())
                                        .or()
                                        .like(SysDeptEntity::getEmail, queryRequest.getKeyWord())
                )
                .eq(Objects.nonNull(queryRequest.getParentId()), SysDeptEntity::getParentId, queryRequest.getParentId())
                .like(StringUtils.hasText(queryRequest.getDeptCode()), SysDeptEntity::getDeptCode, queryRequest.getDeptCode())
                .like(StringUtils.hasText(queryRequest.getDeptName()), SysDeptEntity::getDeptName, queryRequest.getDeptName())
                .eq(Objects.nonNull(queryRequest.getDeptStatus()), SysDeptEntity::getDeptStatus, queryRequest.getDeptStatus())
                .like(StringUtils.hasText(queryRequest.getPhone()), SysDeptEntity::getPhone, queryRequest.getPhone())
                .like(StringUtils.hasText(queryRequest.getEmail()), SysDeptEntity::getPhone, queryRequest.getEmail())
        ;
        // @formatter:on
        return list(queryWrapper);
    }

    /**
     * 更新部门主管岗位id
     *
     * @param deptId       部门id
     * @param leaderPostId 主管岗位id
     */
    @Override
    public void updateLeaderPostId(Long deptId, Long leaderPostId) {
        // @formatter:off
        LambdaUpdateWrapper<SysDeptEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .set(SysDeptEntity::getLeaderPostId, leaderPostId)
                .eq(SysDeptEntity::getId, deptId);
        // @formatter:on
        update(updateWrapper);
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