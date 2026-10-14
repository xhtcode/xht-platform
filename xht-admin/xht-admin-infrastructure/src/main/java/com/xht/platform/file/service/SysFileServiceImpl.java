package com.xht.platform.file.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.framework.utils.AssertUtils;
import com.xht.platform.file.converter.SysFileConverter;
import com.xht.platform.file.dao.SysFileDao;
import com.xht.platform.file.domain.query.SysFileQuery;
import com.xht.platform.file.domain.response.SysFileResponse;
import com.xht.platform.file.entity.SysFileEntity;
import com.xht.platform.file.enums.FileStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 描述： 系统文件服务实现
 * <p>
 * 提供系统文件记录（sys_file）的元数据 CRUD；围绕文件内容的操作类业务
 * （上传、下载、预览、访问权限等）见 {@link FileStorageServiceImpl}；
 * 修改与删除操作前先校验文件记录是否存在（不存在时抛出 {@link BusinessErrorCode#DATA_NOT_EXIST}）；
 * 新增时对未传字段补充默认值（文件状态默认启用、文件上传时间默认当前时间）；
 * 修改采用 MyBatis-Plus 非空字段更新策略，表单未传字段保持原值不变
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class SysFileServiceImpl implements ISysFileService {

    private final SysFileDao sysFileDao;

    private final SysFileConverter sysFileConverter;

    /**
     * 根据文件ID删除文件记录（逻辑删除）
     *
     * @param fileId 文件ID
     */
    @Override
    public void removeById(Long fileId) {
        AssertUtils.notNull(fileId, BusinessErrorCode.PARAM_ERROR);
        AssertUtils.notNull(sysFileDao.existsById(fileId), BusinessErrorCode.FILE_NOT_EXIST);
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
        return sysFileConverter.toResponse(sysFileDao.findById(fileId));
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

}
