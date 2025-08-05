package com.xht.system.modules.dept.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.xht.framework.core.enums.SystemFlagEnums;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.system.modules.dept.dao.SysPostDao;
import com.xht.system.modules.dept.dao.mapper.SysPostMapper;
import com.xht.system.modules.dept.domain.entity.SysPostEntity;
import com.xht.system.modules.dept.domain.request.SysPostFormRequest;
import com.xht.system.modules.dept.domain.request.SysPostQueryRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 部门岗位管理
 *
 * @author xht
 **/
@Slf4j
@Repository
public class SysPostDaoImpl extends MapperRepositoryImpl<SysPostMapper, SysPostEntity> implements SysPostDao {

    /**
     * 判断岗位编码是否存在
     *
     * @param postCode 岗位编码
     * @param postId   岗位ID
     * @return true：存在；false：不存在
     */
    @Override
    public Boolean existsPostCode(String postCode, Long postId) {
        LambdaQueryWrapper<SysPostEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysPostEntity::getPostCode, postCode)
                .ne(Objects.nonNull(postId), SysPostEntity::getId, postId);
        return SqlHelper.retBool(count(lambdaQueryWrapper));
    }

    /**
     * 更新岗位信息
     *
     * @param formRequest 岗位信息
     * @return true：成功；false：失败
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateFormRequest(SysPostFormRequest formRequest) {
        LambdaUpdateWrapper<SysPostEntity> updateWrapper = new LambdaUpdateWrapper<>();
        // @formatter:off
        updateWrapper
                .set(SysPostEntity::getPostCode, formRequest.getPostCode())
                .set(SysPostEntity::getPostName, formRequest.getPostName())
                .set(SysPostEntity::getPostSort, formRequest.getPostSort())
                .set(SysPostEntity::getRemark, formRequest.getRemark())
                .eq(SysPostEntity::getId, formRequest.getId());
        // @formatter:on
        return update(updateWrapper);
    }


    /**
     * 验证部门岗位是否系统内置
     *
     * @param deptPostId 部门岗位ID
     * @param systemFlag 系统内置标识
     * @return 系统内置标识
     */
    @Override
    public Boolean validateSystemFlag(Long deptPostId, SystemFlagEnums systemFlag) {
        LambdaQueryWrapper<SysPostEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(SysPostEntity::getSystemFlag);
        lambdaQueryWrapper.eq(SysPostEntity::getId, deptPostId);
        lambdaQueryWrapper.eq(SysPostEntity::getSystemFlag, systemFlag);
        return SqlHelper.retCount(count(lambdaQueryWrapper)) == 1;
    }

    /**
     * 验证部门岗位是否系统内置
     *
     * @param deptPostIds 部门岗位ID
     * @param systemFlag  系统内置标识
     * @return 系统内置标识
     */
    @Override
    public Boolean validateSystemFlag(List<Long> deptPostIds, SystemFlagEnums systemFlag) {
        LambdaQueryWrapper<SysPostEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(SysPostEntity::getSystemFlag);
        lambdaQueryWrapper.eq(SysPostEntity::getId, deptPostIds);
        lambdaQueryWrapper.eq(SysPostEntity::getSystemFlag, systemFlag);
        return SqlHelper.retCount(count(lambdaQueryWrapper)) == deptPostIds.size();
    }

    /**
     * 分页查询部门岗位信息
     *
     * @param page         分页信息
     * @param queryRequest 查询请求参数
     * @return 分页数据
     */
    @Override
    public Page<SysPostEntity> queryPageRequest(Page<SysPostEntity> page, SysPostQueryRequest queryRequest) {
        LambdaQueryWrapper<SysPostEntity> queryWrapper = new LambdaQueryWrapper<>();
        // @formatter:off
        queryWrapper.and(
                        StringUtils.hasText(queryRequest.getKeyWord()), wrapper -> wrapper.or()
                                .like(SysPostEntity::getPostCode, queryRequest.getKeyWord())
                                .or()
                                .like(SysPostEntity::getPostName, queryRequest.getKeyWord())
                )
                .like(StringUtils.hasText(queryRequest.getPostCode()), SysPostEntity::getPostCode, queryRequest.getPostCode())
                .like(StringUtils.hasText(queryRequest.getPostName()), SysPostEntity::getPostName, queryRequest.getPostName())
        ;
        // @formatter:on
        return page(page, queryWrapper);
    }



    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<SysPostEntity, ?> getFieldId() {
        return SysPostEntity::getId;
    }
}
