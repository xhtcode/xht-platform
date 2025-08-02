package com.xht.generate.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.generate.domain.entity.GenProjectTemplateEntity;
import com.xht.generate.domain.request.GenProjectTemplateFormRequest;
import com.xht.generate.domain.request.GenProjectTemplateQueryRequest;


/**
 * 项目模板管理 Dao
 *
 * @author xht
 **/
public interface GenProjectTemplateDao extends MapperRepository<GenProjectTemplateEntity> {

            /**
                         * 更新菜单信息
                         *
                         * @param formRequest 菜单信息
                         * @return 是否成功
                         */
                        Boolean updateFormRequest(GenProjectTemplateFormRequest formRequest);

                        /**
                         * 分页查询菜单
                         *
                         * @param page         分页信息
                         * @param queryRequest 菜单查询请求参数
                         * @return 菜单分页信息
                         */
                        Page<GenProjectTemplateEntity> queryPageRequest(Page<GenProjectTemplateEntity> page, GenProjectTemplateQueryRequest queryRequest);

            }
