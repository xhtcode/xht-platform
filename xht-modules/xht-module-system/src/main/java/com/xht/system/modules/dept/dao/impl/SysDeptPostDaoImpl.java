package com.xht.system.modules.dept.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.xht.framework.core.enums.SystemFlagEnums;
import com.xht.framework.core.exception.BusinessException;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.system.modules.dept.dao.SysDeptPostDao;
import com.xht.system.modules.dept.dao.mapper.SysDeptPostMapper;
import com.xht.system.modules.dept.domain.entity.SysDeptPostEntity;
import com.xht.system.modules.dept.domain.request.SysDeptPostFormRequest;
import com.xht.system.modules.dept.domain.request.SysDeptPostQueryRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 部门岗位管理
 *
 * @author xht
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class SysDeptPostDaoImpl extends MapperRepositoryImpl<SysDeptPostMapper, SysDeptPostEntity> implements SysDeptPostDao {

    /**
     * 判断岗位编码是否存在
     *
     * @param deptId   部门ID
     * @param postCode 岗位编码
     * @param postId   岗位ID
     * @return true：存在；false：不存在
     */
    @Override
    public Boolean existsPostCode(Long deptId, String postCode, Long postId) {
        LambdaQueryWrapper<SysDeptPostEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysDeptPostEntity::getPostCode, postCode)
                .eq(SysDeptPostEntity::getDeptId, deptId)
                .ne(Objects.nonNull(postId), SysDeptPostEntity::getId, postId);
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
    public Boolean updateFormRequest(SysDeptPostFormRequest formRequest) {
        LambdaUpdateWrapper<SysDeptPostEntity> updateWrapper = new LambdaUpdateWrapper<>();
        // @formatter:off
        updateWrapper.set(SysDeptPostEntity::getDeptId, formRequest.getDeptId())
                .set(SysDeptPostEntity::getPostCode, formRequest.getPostCode())
                .set(SysDeptPostEntity::getPostName, formRequest.getPostName())
                .set(SysDeptPostEntity::getPostSort, formRequest.getPostSort())
                .set(SysDeptPostEntity::getRemark, formRequest.getRemark())
                .eq(SysDeptPostEntity::getId, formRequest.getId());
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
        LambdaQueryWrapper<SysDeptPostEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(SysDeptPostEntity::getSystemFlag);
        lambdaQueryWrapper.eq(SysDeptPostEntity::getId, deptPostId);
        lambdaQueryWrapper.eq(SysDeptPostEntity::getSystemFlag, systemFlag);
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
        LambdaQueryWrapper<SysDeptPostEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(SysDeptPostEntity::getSystemFlag);
        lambdaQueryWrapper.eq(SysDeptPostEntity::getId, deptPostIds);
        lambdaQueryWrapper.eq(SysDeptPostEntity::getSystemFlag, systemFlag);
        return SqlHelper.retCount(count(lambdaQueryWrapper)) == deptPostIds.size();
    }

    /**
     * 判断部门下是否存在系统内置的岗位
     *
     * @param deptId 部门ID
     * @return true：存在；false：不存在
     */
    @Override
    public Boolean existsDeptPost(Long deptId) {
        LambdaQueryWrapper<SysDeptPostEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysDeptPostEntity::getDeptId, deptId);
        lambdaQueryWrapper.eq(SysDeptPostEntity::getSystemFlag, SystemFlagEnums.NO);
        return exists(lambdaQueryWrapper);
    }

    /**
     * 判断部门岗位是否存在
     *
     * @param deptId 部门ID
     * @param postId 岗位ID
     * @return true：存在；false：不存在
     */
    @Override
    public Boolean existsDeptPost(Long deptId, Long postId) {
        LambdaQueryWrapper<SysDeptPostEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysDeptPostEntity::getDeptId, deptId);
        lambdaQueryWrapper.eq(SysDeptPostEntity::getId, postId);
        return exists(lambdaQueryWrapper);
    }

    /**
     * 根据部门ID和岗位编码查询岗位信息
     *
     * @param deptId 部门ID
     * @param postId 岗位ID
     * @return 岗位信息
     */
    @Override
    public SysDeptPostEntity findPostByDeptIdAndPostId(Long deptId, Long postId) {
        LambdaQueryWrapper<SysDeptPostEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // @formatter:off
        lambdaQueryWrapper.select(
                SysDeptPostEntity::getId,
                SysDeptPostEntity::getPostCode,
                SysDeptPostEntity::getPostName,
                SysDeptPostEntity::getPostSort,
                SysDeptPostEntity::getPostStatus,
                SysDeptPostEntity::getPostLimit,
                SysDeptPostEntity::getPostHave,
                SysDeptPostEntity::getSystemFlag
        );
        // @formatter:on
        lambdaQueryWrapper.eq(SysDeptPostEntity::getDeptId, deptId)
                .eq(SysDeptPostEntity::getId, postId);
        return getOne(lambdaQueryWrapper);
    }

    /**
     * 根据岗位id查询部门岗位信息
     *
     * @param id 岗位id
     * @return 部门岗位信息
     */
    @Override
    public SysDeptPostEntity forUpdateById(Long id) {
        return baseMapper.forUpdateById(id);
    }

    /**
     * 更新岗位拥有人数
     *
     * @param postId   岗位id
     * @param postHave 新的岗位拥有人数
     * @return true：成功；false：失败
     */
    @Override
    public Boolean updatePostHave(Long postId, int postHave) {
        LambdaUpdateWrapper<SysDeptPostEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(SysDeptPostEntity::getPostHave, postHave)
                .eq(SysDeptPostEntity::getId, postId);
        return update(updateWrapper);
    }

    /**
     * 查询当前岗位人数是否超过限制
     *
     * @param postId 岗位id
     * @return true：超过限制；false：未超过限制
     */
    @Override
    public Boolean validatePostLimit(Long postId) {
        LambdaQueryWrapper<SysDeptPostEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(SysDeptPostEntity::getPostLimit, SysDeptPostEntity::getPostHave);
        lambdaQueryWrapper.eq(SysDeptPostEntity::getId, postId);
        SysDeptPostEntity one = getOne(lambdaQueryWrapper);
        if (Objects.isNull(one)) {
            throw new BusinessException("岗位不存在");
        }
        return one.getPostHave() + 1 > one.getPostLimit();
    }

    /**
     * 分页查询部门岗位信息
     *
     * @param page         分页信息
     * @param queryRequest 查询请求参数
     * @return 分页数据
     */
    @Override
    public Page<SysDeptPostEntity> queryPageRequest(Page<SysDeptPostEntity> page, SysDeptPostQueryRequest queryRequest) {
        LambdaQueryWrapper<SysDeptPostEntity> queryWrapper = new LambdaQueryWrapper<>();
        // @formatter:off
        queryWrapper.and(
                        StringUtils.hasText(queryRequest.getKeyWord()), wrapper -> wrapper.or()
                                .like(SysDeptPostEntity::getPostCode, queryRequest.getKeyWord())
                                .or()
                                .like(SysDeptPostEntity::getPostName, queryRequest.getKeyWord())
                )
                .like(StringUtils.hasText(queryRequest.getPostCode()), SysDeptPostEntity::getPostCode, queryRequest.getPostCode())
                .like(StringUtils.hasText(queryRequest.getPostName()), SysDeptPostEntity::getPostName, queryRequest.getPostName())
                .eq(SysDeptPostEntity::getDeptId, queryRequest.getDeptId())
        ;
        // @formatter:on
        return page(page, queryWrapper);
    }

    /**
     * 根据部门ID查询岗位信息
     *
     * @param deptId 部门ID
     * @return 岗位信息
     */
    @Override
    public List<SysDeptPostEntity> listByDeptId(String deptId) {
        // @formatter:off
        LambdaQueryWrapper<SysDeptPostEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(
                SysDeptPostEntity::getId,
                SysDeptPostEntity::getPostCode,
                SysDeptPostEntity::getPostName,
                SysDeptPostEntity::getPostStatus
        );
        queryWrapper.eq(SysDeptPostEntity::getDeptId, deptId);
        // @formatter:on
        return list(queryWrapper);
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<SysDeptPostEntity, ?> getFieldId() {
        return SysDeptPostEntity::getId;
    }
}
