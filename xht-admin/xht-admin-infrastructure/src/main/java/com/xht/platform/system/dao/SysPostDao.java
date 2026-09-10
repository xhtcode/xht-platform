package com.xht.platform.system.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.platform.system.domain.form.SysPostForm;
import com.xht.platform.system.domain.query.SysPostQuery;
import com.xht.platform.system.entity.SysPostEntity;

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
     * @param postId 岗位ID
     * @param form   岗位信息
     */
    void updateFormRequest(Long postId, SysPostForm form);

    /**
     * 分页查询部门岗位信息
     *
     * @param page  分页信息
     * @param query 查询请求参数
     * @return 分页数据
     */
    Page<SysPostEntity> findPageList(Page<SysPostEntity> page, SysPostQuery query);

    /**
     * 根据部门ID查询岗位列表
     *
     * @param deptId 部门ID
     * @return 岗位列表
     */
    List<SysPostEntity> findListByDeptId(Long deptId);

}
