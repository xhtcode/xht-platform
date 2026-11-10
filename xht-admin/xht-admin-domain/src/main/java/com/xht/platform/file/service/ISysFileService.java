package com.xht.platform.file.service;

import com.xht.framework.common.domain.response.PageResponse;
import com.xht.platform.common.file.bo.ChunkMergeBO;
import com.xht.platform.common.file.bo.ChunkUploadBO;
import com.xht.platform.common.file.bo.UploadBO;
import com.xht.platform.file.domain.form.FilePreviewInitForm;
import com.xht.platform.file.domain.query.SysFileQuery;
import com.xht.platform.file.domain.response.SysFilePreviewResponse;
import com.xht.platform.file.domain.response.SysFilePreviewStatusResponse;
import com.xht.platform.file.domain.response.SysFileResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
     * 查询已上传分片序号列表（断点续传）
     *
     * @param uploadId 分片上传标识
     * @return 已上传分片序号列表（升序），无已上传分片时返回空集合
     */
    List<Integer> findUploadedChunkIndexes(String uploadId);

    /**
     * 分片上传（单分片）
     *
     * @param chunkUploadBo 分片上传参数
     */
    void uploadChunk(ChunkUploadBO<MultipartFile> chunkUploadBo);

    /**
     * 合并文件分片：校验分片完整性，合并为完整文件并生成文件记录
     *
     * @param chunkMergeBo 分片合并参数
     * @return 文件上传响应信息
     */
    SysFileResponse mergeChunks(ChunkMergeBO chunkMergeBo);

    /**
     * 初始化文件预览信息
     * <p>支持登录、匿名公开、分享票据三种入口；返回 previewId、文件信息、能力清单与转换状态</p>
     *
     * @param form     初始化请求
     * @param request  请求
     * @param response 响应
     * @return 文件预览初始化信息
     */
    SysFilePreviewResponse initPreviewInfo(FilePreviewInitForm form, HttpServletRequest request, HttpServletResponse response);

    /**
     * 查询预览状态
     *
     * @param previewId 预览ID
     * @return 预览状态
     */
    SysFilePreviewStatusResponse previewStatus(String previewId);

    /**
     * 文件下载
     *
     * @param response  响应
     * @param previewId 预览ID
     */
    void downloadFile(HttpServletResponse response, String previewId);

    /**
     * 文件预览
     * <p>按 previewId 解析会话与文件，支持 Range 分段请求</p>
     *
     * @param request   请求
     * @param response  响应
     * @param previewId 预览ID
     */
    void filePreview(HttpServletRequest request, HttpServletResponse response, String previewId);
}