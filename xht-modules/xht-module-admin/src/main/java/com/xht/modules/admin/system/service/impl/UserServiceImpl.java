package com.xht.modules.admin.system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.enums.LoginTypeEnums;
import com.xht.framework.core.enums.UserStatusEnums;
import com.xht.framework.core.exception.BusinessException;
import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.code.UserErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.core.utils.secret.MD5Utils;
import com.xht.framework.core.utils.tree.INode;
import com.xht.framework.core.utils.tree.TreeNode;
import com.xht.framework.core.utils.tree.TreeUtils;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.framework.oauth2.utils.SecurityUtils;
import com.xht.framework.security.core.userdetails.BasicUserDetails;
import com.xht.modules.admin.router.RouterUtils;
import com.xht.modules.admin.router.dto.RouterDTO;
import com.xht.modules.admin.system.converter.SysUserConverter;
import com.xht.modules.admin.system.dao.*;
import com.xht.modules.admin.system.domain.form.SysUserDetailForm;
import com.xht.modules.admin.system.domain.form.SysUserForm;
import com.xht.modules.admin.system.domain.form.UpdatePwdFrom;
import com.xht.modules.admin.system.domain.query.SysUserQuery;
import com.xht.modules.admin.system.domain.response.SysMenuResponse;
import com.xht.modules.admin.system.domain.response.SysPostResponse;
import com.xht.modules.admin.system.domain.response.SysUserDetailResponse;
import com.xht.modules.admin.system.domain.response.SysUserResponse;
import com.xht.modules.admin.system.domain.vo.SysUserVO;
import com.xht.modules.admin.system.domain.vo.UserLoginVo;
import com.xht.modules.admin.system.entity.SysRoleEntity;
import com.xht.modules.admin.system.entity.SysUserDetailEntity;
import com.xht.modules.admin.system.entity.SysUserEntity;
import com.xht.modules.admin.system.helper.SysUserHelper;
import com.xht.modules.admin.system.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户Service实现类
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final SysUserDao sysUserDao;

    private final SysUserPostDao sysUserPostDao;

    private final SysUserRoleDao sysUserRoleDao;

    private final SysRoleMenuDao sysRoleMenuDao;

    private final SysUserConverter sysUserConverter;

    private final SysUserDetailDao sysUserDetailDao;

    /**
     * 用户注册
     *
     * @param userForm 用户创建请求对象
     */
    @Override
    public void create(SysUserForm userForm) {
        SysUserDetailForm detail = userForm.getProfile();
        String idCard = detail.getIdCard();
        String userPhone = userForm.getUserPhone();
        // 校验当前用户信息是否已经注册过
        boolean userPhoneExists = sysUserDao.checkUserPhoneExists(userPhone, null);
        ThrowUtils.throwIf(userPhoneExists, BusinessErrorCode.DATA_EXIST, "该手机号已注册过账号，若为本人操作，可直接登录或联系客服核实！");
        boolean userIdCardExists = sysUserDetailDao.checkUserIdCardExists(idCard, null);
        ThrowUtils.throwIf(userIdCardExists, BusinessErrorCode.DATA_EXIST, "该身份证号已注册过账号，若为本人操作，可直接登录或联系客服核实！");
        // 格式化数据类型
        SysUserEntity sysUser = SysUserHelper.formatUser(userForm);
        SysUserDetailEntity detailEntity = SysUserHelper.formatUser(detail, idCard, sysUser.getId());
        sysUserDetailDao.saveUserInfo(sysUser, detailEntity);
    }

    /**
     * 删除用户
     *
     * @param userId 用户ID
     */
    @Override
    public void removeByUserId(Long userId) {
        Boolean exists = sysUserDao.exists(SysUserEntity::getId, userId);
        ThrowUtils.throwIf(exists, BusinessErrorCode.DATA_NOT_EXIST, "用户不存在");
        sysUserDao.removeById(userId);
        sysUserDetailDao.removeByUserId(userId);
    }

    /**
     * 更新用户信息
     *
     * @param userForm 用户更新请求对象
     */
    @Override
    public void update(SysUserForm userForm) {
        Long userId = userForm.getId();
        SysUserDetailForm detail = userForm.getProfile();
        String idCard = detail.getIdCard();
        String userPhone = userForm.getUserPhone();
        Boolean userExists = sysUserDao.exists(SysUserEntity::getId, userId);
        ThrowUtils.throwIf(!userExists, BusinessErrorCode.DATA_NOT_EXIST);
        Boolean userInfoExists = sysUserDetailDao.exists(SysUserDetailEntity::getUserId, userId);
        ThrowUtils.throwIf(!userInfoExists, BusinessErrorCode.DATA_NOT_EXIST);
        // 校验当前用户信息是否已经注册过
        boolean userPhoneExists = sysUserDao.checkUserPhoneExists(userPhone, userId);
        ThrowUtils.throwIf(userPhoneExists, BusinessErrorCode.DATA_EXIST, "该手机号已注册过账号，若为本人操作，可直接登录或联系客服核实！");
        boolean userIdCardExists = sysUserDetailDao.checkUserIdCardExists(idCard, userId);
        ThrowUtils.throwIf(userIdCardExists, BusinessErrorCode.DATA_EXIST, "该身份证号已注册过账号，若为本人操作，可直接登录或联系客服核实！");
        // 格式化数据类型
        SysUserEntity sysUser = SysUserHelper.formatUser(userForm);
        SysUserDetailEntity detailEntity = SysUserHelper.formatUser(detail, idCard, userId);
        sysUserDetailDao.updateUserInfo(sysUser, detailEntity);
    }

    /**
     * 重置密码
     *
     * @param userId 用户ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(Long userId) {
        sysUserDao.updatePassword(userId, MD5Utils.generateSignature("123456"));
    }

    /**
     * 修改密码
     *
     * @param form 用户更新请求对象
     */
    @Override
    public void updatePassword(UpdatePwdFrom form) {
        // todo 登录现在未实现用户id获取不到，先写死
        Long userId = 1L;
        SysUserEntity sysUserEntity = sysUserDao.findOptionalById(userId).orElseThrow(() -> new BusinessException(UserErrorCode.DATA_NOT_EXIST, "用户不存在"));
        // todo 后期补充用户状态判断
        String oldPassword = form.getOldPassword();
        String newPassword = form.getNewPassword();
        String confirmPassword = form.getConfirmPassword();
        String md5OldPassword = MD5Utils.generateSignature(oldPassword);
        if (!StringUtils.equals(md5OldPassword, sysUserEntity.getPassWord())) {
            throw new BusinessException(UserErrorCode.PASSWORD_ERROR, "旧密码错误");
        }
        if (!StringUtils.equals(newPassword, confirmPassword)) {
            throw new BusinessException(UserErrorCode.PASSWORD_ERROR, "两次密码输入不一致");
        }
        sysUserDao.updatePassword(userId, MD5Utils.generateSignature(form.getNewPassword()));
    }

    /**
     * 更新用户状态
     *
     * @param userId 用户ID
     * @param status 状态
     */
    @Override
    public void updateStatus(Long userId, UserStatusEnums status) {
        Boolean exists = sysUserDao.exists(SysUserEntity::getId, userId);
        ThrowUtils.throwIf(exists, UserErrorCode.DATA_NOT_EXIST, "用户不存在");
        sysUserDao.updateStatus(userId, status);
    }

    /**
     * 根据ID查找用户
     *
     * @param userId 用户ID
     * @return 找到的用户对象，不存在时返回null
     */
    @Override
    public SysUserVO findByUserId(Long userId) {
        ThrowUtils.notNull(userId, "用户ID不能为空");
        SysUserVO sysUserVO = sysUserDao.findInfoByUserId(userId);
        ThrowUtils.notNull(sysUserVO, "查询不到用户信息!");
        if (Objects.isNull(sysUserVO.getProfile())) {
            SysUserDetailResponse sysUserDetailResponse = new SysUserDetailResponse();
            sysUserDetailResponse.setUserId(sysUserVO.getId());
            sysUserVO.setProfile(sysUserDetailResponse);
        }
        List<SysPostResponse> deptPostVo = sysUserPostDao.getPostByUserId(userId);
        sysUserVO.setPostInfos(deptPostVo);
        return sysUserVO;
    }

    /**
     * 根据查询条件分页查找用户
     *
     * @param query 用户查询请求对象
     * @return 用户对象分页结果
     */
    @Override
    public PageResponse<SysUserResponse> findPageList(SysUserQuery query) {
        Page<SysUserEntity> sysUserEntityPage = sysUserDao.findPageList(PageTool.getPage(query), query);
        return sysUserConverter.toResponse(sysUserEntityPage);
    }

    /**
     * 根据用户名和登录类型获取用户信息
     *
     * @param username  用户名
     * @param loginType 登录类型
     * @return 用户信息
     */
    @Override
    public UserLoginVo loadUserByUsername(String username, LoginTypeEnums loginType) {
        if (Objects.isNull(loginType) || Objects.equals(loginType, LoginTypeEnums.WECHAT) || Objects.equals(loginType, LoginTypeEnums.QQ)) {
            throw new BusinessException("用户名或密码错误.");
        }
        UserLoginVo loginVo = sysUserDao.findByUsernameAndLoginType(username, loginType);
        if (Objects.isNull(loginVo)) {
            throw new BusinessException("用户名或密码错误.");
        }
        List<SysRoleEntity> roles = sysUserRoleDao.findRoleListByUserId(loginVo.getId());
        Set<String> menuButtonCodes = sysRoleMenuDao.findPermissionCodeByUserId(loginVo.getId());
        if (!CollectionUtils.isEmpty(roles)) {
            loginVo.setRoleCodes(roles.stream().map(SysRoleEntity::getRoleCode).collect(Collectors.toSet()));
        }
        if (!CollectionUtils.isEmpty(menuButtonCodes)) {
            loginVo.setMenuButtonCodes(menuButtonCodes);
        }
        return loginVo;
    }


    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @Override
    public List<INode<Long>> getRouters() {
        BasicUserDetails user = SecurityUtils.getUser();
        List<SysMenuResponse> menus = sysRoleMenuDao.findRouterByUserId(user.getUserId());
        if (CollectionUtils.isEmpty(menus)) {
            return Collections.emptyList();
        }
        List<INode<Long>> result = new ArrayList<>(menus.size());
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

}
