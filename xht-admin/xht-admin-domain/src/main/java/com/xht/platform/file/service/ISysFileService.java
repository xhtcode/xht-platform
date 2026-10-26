package com.xht.platform.file.service;

import com.xht.framework.common.domain.response.PageResponse;
import com.xht.platform.common.file.bo.UploadBO;
import com.xht.platform.file.domain.query.SysFileQuery;
import com.xht.platform.file.domain.response.SysFileResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * 描述： 系统文件服务接口
 * <p>
 * 提供系统文件记录（sys_file）的删除、单条查询、分页查询与业务关联/解绑能力（元数据管理）；
 *
 * @author xht
 **/
public interface ISysFileService {

    /**
     * 根据文件ID删除文件记录（逻辑删除），并同步清理物理文件
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

    /**
     * 文件上传
     *
     * @param uploadBo 文件上传参数
     * @return 文件上传响应信息
     */
    SysFileResponse uploadMultipartFile(UploadBO<MultipartFile> uploadBo);

    /**
     * 文件下载
     *
     * @param response       响应
     * @param fileStorageKey 文件存储Key
     */
    void downloadFile(HttpServletResponse response, String fileStorageKey);

}