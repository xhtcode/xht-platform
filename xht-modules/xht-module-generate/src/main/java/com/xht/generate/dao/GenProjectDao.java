package com.xht.generate.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.generate.domain.entity.GenProjectEntity;
import com.xht.generate.domain.request.GenProjectFormRequest;
import com.xht.generate.domain.request.GenProjectQueryRequest;


/**
 * 项目管理 Dao
 *
 * @author xht
 **/
public interface GenProjectDao extends MapperRepository<GenProjectEntity> {

            /**
                         * 更新菜单信息
                         *
                         * @param formRequest 菜单信息
                         * @return 是否成功
                         */
                        Boolean updateFormRequest(GenProjectFormRequest formRequest);

                        /**
                         * 分页查询菜单
                         *
                         * @param page         分页信息
                         * @param queryRequest 菜单查询请求参数
                         * @return 菜单分页信息
                         */
                        Page<GenProjectEntity> queryPageRequest(Page<GenProjectEntity> page, GenProjectQueryRequest queryRequest);

            }
