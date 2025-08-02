package com.xht.generate.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.generate.domain.entity.GenLogEntity;
import com.xht.generate.domain.request.GenLogFormRequest;
import com.xht.generate.domain.request.GenLogQueryRequest;


/**
 * 生成日志管理 Dao
 *
 * @author xht
 **/
public interface GenLogDao extends MapperRepository<GenLogEntity> {

            /**
                         * 更新菜单信息
                         *
                         * @param formRequest 菜单信息
                         * @return 是否成功
                         */
                        Boolean updateFormRequest(GenLogFormRequest formRequest);

                        /**
                         * 分页查询菜单
                         *
                         * @param page         分页信息
                         * @param queryRequest 菜单查询请求参数
                         * @return 菜单分页信息
                         */
                        Page<GenLogEntity> queryPageRequest(Page<GenLogEntity> page, GenLogQueryRequest queryRequest);

            }
