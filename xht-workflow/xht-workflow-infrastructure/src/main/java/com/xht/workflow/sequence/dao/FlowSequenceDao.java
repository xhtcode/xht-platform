package com.xht.workflow.sequence.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.workflow.sequence.domain.form.FlowSequenceForm;
import com.xht.workflow.sequence.domain.query.FlowSequencePageQuery;
import com.xht.workflow.sequence.entity.FlowSequenceEntity;

/**
 * 描述：流程序列管理
 *
 * @author xht
 **/
public interface FlowSequenceDao extends MapperRepository<FlowSequenceEntity> {

    /**
     * 根据序列id修改 序列
     *
     * @param id 序列ID
     * @param oldCurrentValue 旧值
     * @param newCurrentValue 新值
     * @return 影响行数
     */
    boolean updateSequence(Long id, int oldCurrentValue, int newCurrentValue);

    /**
     * 更新流程序列
     *
     * @param form 流程序列信息
     */
    void updateRequest(FlowSequenceForm form);

    /**
     * 校验流程序列编码是否重复
     *
     * @param id           流程序列ID
     * @param sequenceCode 流程序列编码
     * @return true-存在，false-不存在
     */
    Boolean checkSequenceCode(Long id, String sequenceCode);

    /**
     * 根据序列code查询序列信息
     *
     * @param sequenceCode 序列编码
     * @return 序列信息
     */
    FlowSequenceEntity findBySequenceCode(String sequenceCode);

    /**
     * 分页查询流程序列列表
     *
     * @param page  分页参数
     * @param query 查询参数
     * @return 流程序列列表
     */
    Page<FlowSequenceEntity> findPageList(Page<FlowSequenceEntity> page, FlowSequencePageQuery query);


}
