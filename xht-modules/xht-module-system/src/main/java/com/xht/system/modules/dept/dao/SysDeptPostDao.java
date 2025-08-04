package com.xht.system.modules.dept.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.enums.SystemFlagEnums;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.system.modules.dept.domain.entity.SysDeptPostEntity;
import com.xht.system.modules.dept.domain.request.SysDeptPostFormRequest;
import com.xht.system.modules.dept.domain.request.SysDeptPostQueryRequest;

import java.util.List;

/**
 * 部门岗位管理
 *
 * @author xht
 **/
public interface SysDeptPostDao extends MapperRepository<SysDeptPostEntity> {

    /**
     * 判断岗位编码是否存在
     *
     * @param deptId   部门ID
     * @param postCode 岗位编码
     * @param postId   岗位ID
     * @return true：存在；false：不存在
     */
    Boolean existsPostCode(Long deptId, String postCode, Long postId);
    /**
     * 更新岗位信息
     *
     * @param formRequest 岗位信息
     * @return true：成功；false：失败
     */
    Boolean updateFormRequest(SysDeptPostFormRequest formRequest);

    /**
     * 验证部门岗位是否系统内置
     *
     * @param deptPostId 部门岗位ID
     * @param systemFlag 系统内置标识
     * @return 系统内置标识
     */
    Boolean validateSystemFlag(Long deptPostId, SystemFlagEnums systemFlag);

    /**
     * 验证部门岗位是否系统内置
     *
     * @param deptPostIds 部门岗位ID
     * @param systemFlag  系统内置标识
     * @return 系统内置标识
     */
    Boolean validateSystemFlag(List<Long> deptPostIds, SystemFlagEnums systemFlag);

    /**
     * 判断部门下是否存在系统内置的岗位
     *
     * @param deptId 部门ID
     * @return true：存在；false：不存在
     */
    Boolean existsDeptPost(Long deptId);

    /**
     * 判断部门岗位是否存在
     *
     * @param deptId 部门ID
     * @param postId 岗位ID
     * @return true：存在；false：不存在
     */
    Boolean existsDeptPost(Long deptId, Long postId);

    /**
     * 根据部门ID和岗位编码查询岗位信息
     *
     * @param deptId 部门ID
     * @param postId 岗位ID
     * @return 岗位信息
     */
    SysDeptPostEntity findPostByDeptIdAndPostId(Long deptId, Long postId);

    /**
     * 根据岗位id查询部门岗位信息
     *
     * @param id 岗位id
     * @return 部门岗位信息
     */
    SysDeptPostEntity forUpdateById(Long id);

    /**
     * 分页查询部门岗位信息
     *
     * @param page         分页信息
     * @param queryRequest 查询请求参数
     * @return 分页数据
     */
    Page<SysDeptPostEntity> queryPageRequest(Page<SysDeptPostEntity> page, SysDeptPostQueryRequest queryRequest);

    /**
     * 根据部门ID查询岗位信息
     *
     * @param deptId 部门ID
     * @return 岗位信息
     */
    List<SysDeptPostEntity> listByDeptId(String deptId);
}
