package com.xht.platform.user;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.common.constant.StringConstant;
import com.xht.framework.common.enums.UserTypeEnum;
import com.xht.framework.file.core.MultipartFileUploadBO;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.framework.oauth2.utils.SecurityUtils;
import com.xht.framework.security.core.userdetails.BasicUserDetails;
import com.xht.framework.utils.AssertUtils;
import com.xht.framework.utils.CollectionUtils;
import com.xht.framework.utils.StringUtils;
import com.xht.framework.utils.tree.INode;
import com.xht.framework.utils.tree.TreeNode;
import com.xht.framework.utils.tree.TreeUtils;
import com.xht.platform.common.router.dto.RouterDTO;
import com.xht.platform.file.dao.FileStorageDao;
import com.xht.platform.file.entity.SysFileEntity;
import com.xht.platform.file.enums.FileUsedEnum;
import com.xht.platform.file.properties.FilePreviewProperties;
import com.xht.platform.login.converter.LoginLogConverter;
import com.xht.platform.login.dao.SysLoginLogDao;
import com.xht.platform.login.domain.response.UserLoginLogResponse;
import com.xht.platform.login.entity.SysLoginLogEntity;
import com.xht.platform.system.converter.SysMenuConverter;
import com.xht.platform.system.dao.SysMenuDao;
import com.xht.platform.system.dao.SysRoleMenuDao;
import com.xht.platform.system.dao.SysUserDao;
import com.xht.platform.system.dao.SysUserRoleDao;
import com.xht.platform.system.domain.response.SysMenuResponse;
import com.xht.platform.system.domain.response.SysUserDetailResponse;
import com.xht.platform.system.domain.vo.SysUserVO;
import com.xht.platform.system.entity.SysMenuEntity;
import com.xht.platform.system.utils.RouterUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 描述：用户信息接口实现类
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements IUserProfileService {

    private static final String AVATAR_DATE_PATTERN = "yyyyMMdd_HHmmss";

    private final SysUserDao sysUserDao;

    private final SysUserRoleDao sysUserRoleDao;

    private final SysRoleMenuDao sysRoleMenuDao;

    private final SysMenuDao sysMenuDao;

    private final SysMenuConverter sysMenuConverter;

    private final FileStorageDao fileStorageDao;

    private final SysLoginLogDao sysLoginLogDao;

    private final LoginLogConverter loginLogConverter;

    private final FilePreviewProperties filePreviewProperties;

    /**
     * 上传用户头像
     *
     * @param request HTTP请求对象
     * @param avatar  头像文件，支持jpg、png等图片格式
     * @return 上传成功后返回文件预览访问地址
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String uploadUserAvatar(HttpServletRequest request, MultipartFile avatar) {
        BasicUserDetails userDetails = SecurityUtils.getUser();
        String username = userDetails.getUsername();
        Long userId = userDetails.getUserId();
        MultipartFileUploadBO multipartFileUploadBO = new MultipartFileUploadBO(request, avatar);
        long fileSize = multipartFileUploadBO.getFileSize();
        AssertUtils.isTrue(fileSize > 0, "上传的头像大小必须大于0!");
        // 头像不能大于1mb
        AssertUtils.isTrue(fileSize <= 1024 * 1024, "上传的头像大小不能大于1MB!");
        FileUsedEnum fileUsed = FileUsedEnum.AVATAR;
        multipartFileUploadBO.setFileUsed(fileUsed.getValue());
        multipartFileUploadBO.setFilePathPrefix(generateAvatarFilePath(username));
        multipartFileUploadBO.setFileDesc(fileUsed.getDesc());
        multipartFileUploadBO.setFileUploadUser(username);
        SysFileEntity sysFileEntity = fileStorageDao.uploadMultipartFile(multipartFileUploadBO);
        String userAvatar = filePreviewProperties.getUserAvatar(sysFileEntity.getFileStorageKey());
        fileStorageDao.removeUserAvatar(username, sysFileEntity.getFileStorageKey());
        sysUserDao.updateUserAvatar(userId, userAvatar);
        fileStorageDao.associateFile(sysFileEntity.getFileStorageKey(), fileUsed.getValue(), fileUsed.getDesc());
        return userAvatar;
    }

    /**
     * 生成用户头像默认存储路径前缀
     * 路径规则：{uploadUser}/avatar/yyyyMMdd_HHmmss
     *
     * @param uploadUser 上传用户标识
     * @return 头像基础路径
     */
    private String generateAvatarFilePath(String uploadUser) {
        String dateNow = DateUtil.format(LocalDateTime.now(), AVATAR_DATE_PATTERN);
        return uploadUser + StringConstant.SEPARATOR_SLASH + FileUsedEnum.AVATAR.getValue() + StringConstant.SEPARATOR_SLASH + dateNow;
    }

    /**
     * 获取当前登录的用户信息
     *
     * @return 用户信息
     */
    @Override
    public SysUserVO getUserProfileInfo() {
        BasicUserDetails userDetails = SecurityUtils.getUser();
        SysUserVO userVo = sysUserDao.findInfoByUserId(userDetails.getUserId());
        AssertUtils.notNull(userVo, "查询不到用户信息!");
        if (Objects.isNull(userVo.getProfile())) {
            SysUserDetailResponse sysUserDetailResponse = new SysUserDetailResponse();
            sysUserDetailResponse.setUserId(userVo.getId());
            userVo.setProfile(sysUserDetailResponse);
        }
        userVo.setRoleCodes(userDetails.getRoleCodes());
        userVo.setMenuButtonCodes(userDetails.getMenuButtonCodes());
        return userVo;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @Override
    public List<INode<Long>> getUserProfileRouters() {
        BasicUserDetails user = SecurityUtils.getUser();
        List<SysMenuResponse> menus;
        if (UserTypeEnum.isAdmin(user.getUserType())) {
            List<SysMenuEntity> menuList = sysMenuDao.selectAdminMenu();
            menus = sysMenuConverter.toResponse(menuList);
        } else {
            List<Long> roleIds = sysUserRoleDao.findBindRoleIds(user.getUserId());
            Set<Long> menuIds = sysRoleMenuDao.findMenuIdByRoleIds(roleIds);
            menus = sysMenuConverter.toResponse(sysMenuDao.findRouterMenus(menuIds));
        }
        List<INode<Long>> result = new ArrayList<>();
        if (CollectionUtils.isEmpty(menus)) {
            return Collections.emptyList();
        }
        for (SysMenuResponse menu : menus) {
            RouterDTO routerDTO = new RouterDTO();
            routerDTO.setPath(menu.getMenuPath());
            routerDTO.setName(StringUtils.emptyToDefault(menu.getViewName(), menu.getId() + ""));
            routerDTO.setComponent(menu.getViewPath());
            routerDTO.setMeta(RouterUtils.generateRouter(menu));
            result.add(new TreeNode<>(menu.getId(), menu.getParentId(), menu.getMenuSort()).setExtra(routerDTO));
        }
        return TreeUtils.buildList(result, Boolean.FALSE);
    }

    /**
     * 获取当前登录用户登录日志
     *
     * @return 登录日志信息
     */
    @Override
    public List<UserLoginLogResponse> getUserProfileLoginLog() {
        Page<SysLoginLogEntity> loginLogEntityIPage = PageTool.getPage(1, 10);
        IPage<SysLoginLogEntity> page = sysLoginLogDao.findPageByUserName(loginLogEntityIPage, SecurityUtils.getUserName());
        return loginLogConverter.toResponse(page.getRecords());
    }

}
