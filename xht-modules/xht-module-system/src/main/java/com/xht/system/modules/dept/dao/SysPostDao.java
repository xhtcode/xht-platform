package com.xht.system.modules.dept.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.enums.SystemFlagEnums;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.system.modules.dept.domain.entity.SysPostEntity;
import com.xht.system.modules.dept.domain.request.SysPostFormRequest;
import com.xht.system.modules.dept.domain.request.SysPostQueryRequest;

import java.util.List;

/**
 * 部门岗位管理
 *
 * @author xht
 **/
public interface SysPostDao extends MapperRepository<SysPostEntity> {

    /**
     * 判断岗位编码是否存在
     *
     * @param postCode 岗位编码
     * @param postId   岗位ID
     * @return true：存在；false：不存在
     */
    Boolean existsPostCode(String postCode, Long postId);
    /**
     * 更新岗位信息
     *
     * @param formRequest 岗位信息
     * @return true：成功；false：失败
     */
    Boolean updateFormRequest(SysPostFormRequest formRequest);

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
     * 分页查询部门岗位信息
     *
     * @param page         分页信息
     * @param queryRequest 查询请求参数
     * @return 分页数据
     */
    Page<SysPostEntity> queryPageRequest(Page<SysPostEntity> page, SysPostQueryRequest queryRequest);

}
