package com.xht.platform.file.service;

import com.xht.framework.common.domain.response.PageResponse;
import com.xht.platform.file.domain.query.SysFileQuery;
import com.xht.platform.file.domain.response.SysFileResponse;

/**
 * 描述： 系统文件服务接口
 * <p>
 * 提供系统文件记录（sys_file）的新增、删除、修改、单条查询与分页查询能力（元数据 CRUD）；
 * 围绕文件内容的操作类业务（上传、下载、预览、访问权限等）见 {@link IFileStorageService}
 *
 * @author xht
 **/
public interface ISysFileService {

    /**
     * 根据文件ID删除文件记录（逻辑删除）
     *
     * @param fileId 文件ID
     */
    void removeById(Long fileId);

    /**
     * 根据文件ID查询文件详情
     *
     * @param fileId 文件ID
     * @return 文件详情
     */
    SysFileResponse findById(Long fileId);

    /**
     * 分页查询文件列表
     *
     * @param query 分页查询参数
     * @return 分页结果
     */
    PageResponse<SysFileResponse> findPageList(SysFileQuery query);

}
