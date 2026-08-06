package com.xht.platform.system.service;

import com.xht.framework.common.domain.response.PageResponse;
import  com.xht.platform.system.domain.form.SysPostForm;
import  com.xht.platform.system.domain.query.SysPostQuery;
import  com.xht.platform.system.domain.response.SysPostResponse;

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
     * @param postId 部门岗位ID
     */
    void removeById(Long postId);
    
    /**
     * 根据ID更新部门岗位
     *
     * @param postId 部门岗位ID
     * @param form 部门岗位更新请求参数
     */
    void updateById(Long postId, SysPostForm form);

    /**
     * 根据ID查询部门岗位
     *
     * @param postId 部门岗位ID
     * @return 部门岗位信息
     */
    SysPostResponse findById(Long postId);

    /**
     * 分页查询部门岗位
     *
     * @param query 部门岗位查询请求参数
     * @return 部门岗位分页信息
     */
    PageResponse<SysPostResponse> findPageList(SysPostQuery query);

}