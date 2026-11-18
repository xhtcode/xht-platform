package com.xht.platform.file.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.framework.utils.AssertUtils;
import com.xht.platform.common.file.bo.UploadBO;
import com.xht.platform.file.converter.SysFileConverter;
import com.xht.platform.file.dao.FileStorageDao;
import com.xht.platform.file.dao.SysFileDao;
import com.xht.platform.file.domain.query.SysFileQuery;
import com.xht.platform.file.domain.response.SysFileResponse;
import com.xht.platform.file.entity.SysFileEntity;
import com.xht.framework.file.enums.FileStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

/**
 * 描述： 系统文件服务实现
 * <p>文件元数据 CRUD、预览会话初始化、状态查询、内容输出与下载</p>
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class SysFileServiceImpl implements ISysFileService {

    private final SysFileDao sysFileDao;

    private final SysFileConverter sysFileConverter;

    private final FileStorageDao fileStorageDao;

    /**
     * 根据文件ID删除文件记录（逻辑删除），并同步删除物理文件
     *
     * @param fileId 文件ID
     */
    @Override
    public void removeById(Long fileId) {
        AssertUtils.notNull(fileId, BusinessErrorCode.PARAM_ERROR);
        SysFileEntity sysFileEntity = sysFileDao.findById(fileId);
        AssertUtils.notNull(sysFileEntity, BusinessErrorCode.FILE_NOT_EXIST);
        // 已关联业务的文件不允许删除
        AssertUtils.isTrue(!Objects.equals(FileStatusEnum.ASSOCIATED, sysFileEntity.getFileStatus()), BusinessErrorCode.FILE_ASSOCIATED, "文件已关联业务，无法删除");
        sysFileDao.updateFileStatusById(fileId, FileStatusEnum.DELETED);
    }

    /**
     * 根据文件ID查询文件详情
     *
     * @param fileId 文件ID
     * @return 文件详情
     */
    @Override
    public SysFileResponse findById(Long fileId) {
        AssertUtils.notNull(fileId, BusinessErrorCode.PARAM_ERROR);
        SysFileEntity sysFileEntity = sysFileDao.findById(fileId);
        AssertUtils.notNull(sysFileEntity, BusinessErrorCode.FILE_NOT_EXIST);
        return sysFileConverter.toResponse(sysFileEntity);
    }

    /**
     * 分页查询文件列表
     *
     * @param query 分页查询参数
     * @return 分页结果
     */
    @Override
    public PageResponse<SysFileResponse> findPageList(SysFileQuery query) {
        Page<SysFileEntity> page = sysFileDao.findPageList(PageTool.getPage(query), query);
        return sysFileConverter.toResponse(page);
    }

    /**
     * 文件上传
     *
     * @param uploadBo 文件上传参数
     * @return 文件信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysFileResponse uploadMultipartFile(UploadBO<MultipartFile> uploadBo) {
        SysFileEntity sysFileEntity = fileStorageDao.uploadMultipartFile(uploadBo);
        return sysFileConverter.toResponse(sysFileEntity);
    }

}
