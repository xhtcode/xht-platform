package com.xht.platform.file.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.framework.utils.AssertUtils;
import com.xht.framework.utils.StringUtils;
import com.xht.platform.file.dao.SysFileDao;
import com.xht.platform.file.dao.SysFileUsedDao;
import com.xht.platform.file.domain.form.SysFileUsedForm;
import com.xht.platform.file.domain.query.SysFileUsedQuery;
import com.xht.platform.file.domain.response.SysFileUsedResponse;
import com.xht.platform.file.entity.SysFileUsedEntity;
import com.xht.platform.file.enums.FileAccessScopeEnum;
import com.xht.platform.file.enums.ImageModeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 描述： 文件用途管理服务
 *
 * @author xht
 **/
@Service
@RequiredArgsConstructor
public class SysFileUsedAdminService implements ISysFileUsedAdminService {

    private static final Pattern CODE_PATTERN = Pattern.compile("^[a-z][a-z0-9_]{1,31}$");

    private final SysFileUsedDao sysFileUsedDao;

    private final SysFileDao sysFileDao;

    private final SysFileUsedService sysFileUsedService;

    /**
     * 启用用途列表
     *
     * @return 用途列表
     */
    public List<SysFileUsedResponse> listEnabled() {
        return sysFileUsedService.listEnabled().stream().map(this::toResponse).toList();
    }

    /**
     * 分页查询用途
     *
     * @param query 分页查询参数
     * @return 分页结果
     */
    public PageResponse<SysFileUsedResponse> findPageList(SysFileUsedQuery query) {
        Page<SysFileUsedEntity> page = sysFileUsedDao.findPageList(PageTool.getPage(query), query);
        return PageTool.createPageVo(page.getCurrent(), page.getSize(), page.getTotal(),
                page.getRecords().stream().map(this::toResponse).toList());
    }

    /**
     * 根据ID查询用途
     *
     * @param id 用途ID
     * @return 用途详情
     */
    public SysFileUsedResponse findById(Long id) {
        SysFileUsedEntity entity = sysFileUsedDao.findById(id);
        AssertUtils.notNull(entity, BusinessErrorCode.FILE_USED_NOT_EXIST);
        return toResponse(entity);
    }

    /**
     * 新增用途
     *
     * @param form 表单
     */
    @Transactional(rollbackFor = Exception.class)
    public void create(SysFileUsedForm form) {
        AssertUtils.notNull(form, BusinessErrorCode.PARAM_ERROR);
        String code = form.getUsedCode();
        if (code == null || !CODE_PATTERN.matcher(code).matches()) {
            throw new BusinessException(BusinessErrorCode.PARAM_ERROR, "用途编码格式不正确");
        }
        if (sysFileUsedDao.existsByCode(code, null)) {
            throw new BusinessException(BusinessErrorCode.PARAM_ERROR, "用途编码已存在");
        }
        SysFileUsedEntity entity = new SysFileUsedEntity();
        entity.setUsedCode(code);
        applyForm(entity, form);
        entity.setBuiltIn(0);
        entity.setConfigVersion(0);
        sysFileUsedDao.save(entity);
        sysFileUsedService.refresh(code);
    }

    /**
     * 更新用途
     *
     * @param id   用途ID
     * @param form 表单
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, SysFileUsedForm form) {
        SysFileUsedEntity entity = sysFileUsedDao.findById(id);
        AssertUtils.notNull(entity, BusinessErrorCode.FILE_USED_NOT_EXIST);
        if (StringUtils.hasText(form.getUsedCode()) && !Objects.equals(form.getUsedCode(), entity.getUsedCode())) {
            throw new BusinessException(BusinessErrorCode.PARAM_ERROR, "用途编码创建后不可修改");
        }
        boolean wasPublic = Objects.equals(entity.getPublicAllowed(), 1);
        applyForm(entity, form);
        sysFileUsedDao.updateById(entity);
        if (wasPublic && !Objects.equals(entity.getPublicAllowed(), 1)) {
            sysFileDao.updateAccessScopeByUsed(entity.getUsedCode(), FileAccessScopeEnum.PRIVATE);
        }
        sysFileUsedService.refresh(entity.getUsedCode());
    }

    /**
     * 停用用途
     *
     * @param id 用途ID
     */
    public void disable(Long id) {
        SysFileUsedEntity entity = sysFileUsedDao.findById(id);
        AssertUtils.notNull(entity, BusinessErrorCode.FILE_USED_NOT_EXIST);
        entity.setEnabled(0);
        sysFileUsedDao.updateById(entity);
        sysFileUsedService.refresh(entity.getUsedCode());
    }

    /**
     * 删除用途
     *
     * @param id 用途ID
     */
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long id) {
        SysFileUsedEntity entity = sysFileUsedDao.findById(id);
        AssertUtils.notNull(entity, BusinessErrorCode.FILE_USED_NOT_EXIST);
        if (Objects.equals(entity.getBuiltIn(), 1)) {
            throw new BusinessException(BusinessErrorCode.PARAM_ERROR, "内置用途不可删除");
        }
        if (sysFileDao.countByUsed(entity.getUsedCode()) > 0) {
            throw new BusinessException(BusinessErrorCode.PARAM_ERROR, "该用途已被文件引用，只能停用");
        }
        sysFileUsedDao.removeById(id);
        sysFileUsedService.refresh(entity.getUsedCode());
    }

    /**
     * 应用表单
     */
    private void applyForm(SysFileUsedEntity entity, SysFileUsedForm form) {
        entity.setUsedName(form.getUsedName());
        entity.setDescription(form.getDescription());
        entity.setEnabled(form.getEnabled() == null ? 1 : form.getEnabled());
        entity.setSort(form.getSort() == null ? 0 : form.getSort());
        entity.setAccessScope(form.getAccessScope() == null ? FileAccessScopeEnum.PRIVATE : form.getAccessScope());
        entity.setPublicAllowed(form.getPublicAllowed() == null ? 0 : form.getPublicAllowed());
        entity.setDownloadAllowed(form.getDownloadAllowed() == null ? 1 : form.getDownloadAllowed());
        entity.setPrintAllowed(form.getPrintAllowed() == null ? 1 : form.getPrintAllowed());
        entity.setShareAllowed(form.getShareAllowed() == null ? 1 : form.getShareAllowed());
        entity.setShareExpireDays(form.getShareExpireDays());
        entity.setShareMaxAccessCount(form.getShareMaxAccessCount());
        ImageModeEnum imageMode = form.getImageMode();
        entity.setImageMode(imageMode == null ? null : imageMode.getValue());
    }

    /**
     * 转响应
     */
    private SysFileUsedResponse toResponse(SysFileUsedEntity entity) {
        SysFileUsedResponse response = new SysFileUsedResponse();
        response.setId(entity.getId());
        response.setUsedCode(entity.getUsedCode());
        response.setUsedName(entity.getUsedName());
        response.setDescription(entity.getDescription());
        response.setBuiltIn(entity.getBuiltIn());
        response.setEnabled(entity.getEnabled());
        response.setSort(entity.getSort());
        response.setAccessScope(entity.getAccessScope());
        response.setPublicAllowed(entity.getPublicAllowed());
        response.setDownloadAllowed(entity.getDownloadAllowed());
        response.setPrintAllowed(entity.getPrintAllowed());
        response.setShareAllowed(entity.getShareAllowed());
        response.setShareExpireDays(entity.getShareExpireDays());
        response.setShareMaxAccessCount(entity.getShareMaxAccessCount());
        response.setImageMode(ImageModeEnum.getByValue(entity.getImageMode()));
        response.setFileCount(sysFileDao.countByUsed(entity.getUsedCode()));
        response.setUpdateTime(entity.getUpdateTime());
        return response;
    }

}
