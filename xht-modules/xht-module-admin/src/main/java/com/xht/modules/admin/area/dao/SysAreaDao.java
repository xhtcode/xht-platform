package com.xht.modules.admin.area.dao;

import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.modules.admin.area.domain.form.SysAreaForm;
import com.xht.modules.admin.area.entity.SysAreaEntity;
import com.xht.modules.admin.area.enums.AreaHasChildEnums;

import java.util.List;

/**
 * 系统管理 - 行政区划
 *
 * @author xht
 **/
public interface SysAreaDao extends MapperRepository<SysAreaEntity> {

    /**
     * 修改节点 hasChild属性
     *
     * @param id       主键
     * @param hasChild 是否有子节点
     */
    void updateHasChild(Long id, AreaHasChildEnums hasChild);

    /**
     * 根据主键`areaCode`更新系统管理-行政区划
     *
     * @param form 系统管理-行政区划表单请求参数
     */
    void updateFormRequest(SysAreaForm form);

    /**
     * 校验区划下是否有子区划
     *
     * @param parentId 上级区划编码
     * @return true:有子区划
     */
    boolean existsChild(Long parentId);

    /**
     * 校验区划下是否有子区划
     *
     * @param areaCode 区划编码
     * @param id       区划ID
     * @return true:有子区划
     */
    boolean existsAreaCode(String areaCode, Long id);

    /**
     * 根据上级区划编码查询子区划
     *
     * @param parentId 上级区划编码
     * @return 子区划列表
     */
    List<SysAreaEntity> listByParentId(Long parentId);

}
