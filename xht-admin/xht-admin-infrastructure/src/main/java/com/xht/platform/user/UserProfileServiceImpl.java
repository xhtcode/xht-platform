package com.xht.platform.user;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.common.enums.UserTypeEnum;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.framework.oauth2.utils.SecurityUtils;
import com.xht.framework.security.core.userdetails.BasicUserDetails;
import com.xht.framework.utils.AssertUtils;
import com.xht.framework.utils.CollectionUtils;
import com.xht.framework.utils.StringUtils;
import com.xht.framework.utils.tree.INode;
import com.xht.framework.utils.tree.TreeNode;
import com.xht.framework.utils.tree.TreeUtils;
import com.xht.platform.common.file.bo.UploadBO;
import com.xht.platform.common.file.constant.SysFileConstant;
import com.xht.platform.common.router.dto.RouterDTO;
import com.xht.platform.file.dao.FileStorageDao;
import com.xht.platform.file.entity.SysFileEntity;
import com.xht.platform.login.converter.LoginLogConverter;
import com.xht.platform.login.dao.SysLoginLogDao;
import com.xht.platform.login.domain.response.UserLoginLogResponse;
import com.xht.platform.login.entity.SysLoginLogEntity;
import com.xht.platform.system.converter.SysMenuConverter;
import com.xht.platform.system.dao.*;
import com.xht.platform.system.domain.response.SysMenuResponse;
import com.xht.platform.system.domain.response.SysUserDetailResponse;
import com.xht.platform.system.domain.vo.SysUserVO;
import com.xht.platform.system.entity.SysMenuEntity;
import com.xht.platform.system.utils.RouterUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    private final SysUserDao sysUserDao;

    private final SysUserRoleDao sysUserRoleDao;

    private final SysRoleMenuDao sysRoleMenuDao;

    private final SysMenuDao sysMenuDao;

    private final SysMenuConverter sysMenuConverter;

    private final FileStorageDao fileStorageDao;

    private final SysLoginLogDao sysLoginLogDao;

    private final LoginLogConverter loginLogConverter;

    /**
     * 上传头像
     *
     * @param avatar 头像文件
     */
    @Override
    public String uploadUserAvatar(MultipartFile avatar) {
        String userName = SecurityUtils.getUserName();
        SysFileEntity sysFileEntity = fileStorageDao
                .uploadMultipartFile(UploadBO
                        .builder(avatar)
                        .fileUsed(SysFileConstant.FILE_USED_AVATAR)
                        .filePathPrefix(userName + "/avatar")
                        .fileUploadUser(userName)
                        .build());
        return "/img/user-avatar/" + sysFileEntity.getFileStorageKey();
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
