package com.xht.system.modules.dept.mapper;

import com.xht.framework.mybatis.mapper.BaseMapperX;
import com.xht.system.modules.dept.domain.entity.SysDeptPostEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 系统部门岗位关系表
 *
 * @author xht
 */
@Mapper
public interface SysDeptPostMapper extends BaseMapperX<SysDeptPostEntity> {

    /**
     * 根据岗位id查询部门岗位信息
     *
     * @param id 岗位id
     * @return 部门岗位信息
     */
    @Select("SELECT id,dept_id,post_code,post_name,post_sort,post_status,post_limit,post_have  FROM sys_dept_post WHERE id = #{id} FOR UPDATE")
    SysDeptPostEntity forUpdateById(Long id);

}



