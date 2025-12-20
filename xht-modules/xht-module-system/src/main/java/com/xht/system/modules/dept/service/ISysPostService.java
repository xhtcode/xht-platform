package com.xht.system.modules.dept.service;

import com.xht.framework.core.domain.response.PageResponse;
import com.xht.system.modules.dept.domain.form.SysPostForm;
import com.xht.system.modules.dept.domain.query.SysPostQuery;
import com.xht.system.modules.dept.domain.response.SysPostResponse;

import java.util.List;

/**
 * 部门岗位岗位Service接口
 *
 * @author xht
 */
public interface ISysPostService {

    /**
     * 创建部门岗位
     *
     * @param form 部门岗位表单请求参数
     */
    void create(SysPostForm form);

    /**
     * 根据ID删除部门岗位
     *
     * @param id 部门岗位ID
     */
    void removeById(Long id);

    /**
     * 根据ID数组批量删除部门岗位
     *
     * @param ids 部门岗位ID数组
     */
    void removeByIds(List<Long> ids);

    /**
     * 根据ID更新部门岗位
     *
     * @param form 部门岗位更新请求参数
     */
    void updateById(SysPostForm form);

    /**
     * 根据ID查询部门岗位
     *
     * @param id 部门岗位ID
     * @return 部门岗位信息
     */
    SysPostResponse findById(Long id);

    /**
     * 分页查询部门岗位
     *
     * @param query 部门岗位查询请求参数
     * @return 部门岗位分页信息
     */
    PageResponse<SysPostResponse> findPageList(SysPostQuery query);

}
