package com.xht.platform.file.service;

import com.xht.framework.common.domain.response.PageResponse;
import com.xht.platform.file.domain.form.SysFileTypeForm;
import com.xht.platform.file.domain.query.SysFileTypeQuery;
import com.xht.platform.file.domain.response.SysFileTypeResponse;

/**
 * 描述： 文件预览类型配置服务接口
 * <p>
 * 提供文件预览类型配置（sys_file_type）的新增、删除、修改、单查、分页与列表查询，
 * 并提供按文件后缀查询预览类型的只读能力，供文件预览流程使用
 *
 * @author xht
 **/
public interface ISysFileTypeService {

    /**
     * 创建文件预览类型配置
     *
     * @param form 文件预览类型配置表单
     */
    void create(SysFileTypeForm form);

    /**
     * 根据ID删除文件预览类型配置
     *
     * @param fileTypeId 配置ID
     */
    void removeById(Long fileTypeId);

    /**
     * 根据文件后缀删除缓存
     *
     * @param fileSuffix 文件后缀
     */
    void removeCache(String fileSuffix);

    /**
     * 根据ID更新文件预览类型配置
     *
     * @param fileTypeId 配置ID
     * @param form       文件预览类型配置表单
     */
    void updateById(Long fileTypeId, SysFileTypeForm form);

    /**
     * 根据ID查询文件预览类型配置
     *
     * @param fileTypeId 配置ID
     * @return 文件预览类型配置
     */
    SysFileTypeResponse findById(Long fileTypeId);

    /**
     * 分页查询文件预览类型配置
     *
     * @param query 分页查询参数
     * @return 分页结果
     */
    PageResponse<SysFileTypeResponse> findPageList(SysFileTypeQuery query);

}
