package com.xht.system.modules.dept.service;

import com.xht.framework.core.domain.response.PageResponse;
import com.xht.system.modules.dept.domain.request.SysDeptPostFormRequest;
import com.xht.system.modules.dept.domain.request.SysDeptPostQueryRequest;
import com.xht.system.modules.dept.domain.response.SysDeptPostResponse;
import com.xht.system.modules.dept.domain.vo.SysDeptPostSimpleVo;

import java.util.List;

/**
 * 部门岗位岗位Service接口
 *
 * @author xht
 */
public interface ISysDeptPostService {
    /**
     * 创建部门岗位
     *
     * @param formRequest 部门岗位表单请求参数
     * @return 操作结果
     */
    Boolean create(SysDeptPostFormRequest formRequest);

    /**
     * 根据ID删除部门岗位
     *
     * @param id 部门岗位ID
     * @return 操作结果
     */
    Boolean removeById(Long id);

    /**
     * 根据ID数组批量删除部门岗位
     *
     * @param ids 部门岗位ID数组
     * @return 操作结果
     */
    Boolean removeByIds(List<Long> ids);

    /**
     * 根据ID更新部门岗位
     *
     * @param formRequest 部门岗位更新请求参数
     * @return 操作结果
     */
    Boolean updateById(SysDeptPostFormRequest formRequest);

    /**
     * 根据ID查询部门岗位
     *
     * @param id 部门岗位ID
     * @return 部门岗位信息
     */
    SysDeptPostResponse getById(Long id);

    /**
     * 分页查询部门岗位
     *
     * @param queryRequest 部门岗位查询请求参数
     * @return 部门岗位分页信息
     */
    PageResponse<SysDeptPostResponse> selectPage(SysDeptPostQueryRequest queryRequest);

    /**
     * 根据部门ID查询部门岗位
     *
     * @param deptId 部门ID
     * @return 部门岗位信息
     */
    List<SysDeptPostSimpleVo> findListByDeptId(String deptId);
}
