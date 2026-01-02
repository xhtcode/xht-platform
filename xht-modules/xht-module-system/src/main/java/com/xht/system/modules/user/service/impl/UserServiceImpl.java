package com.xht.system.modules.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.api.system.user.dto.UserInfoDTO;
import com.xht.framework.cache.service.RedisService;
import com.xht.framework.cache.utils.Keys;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.enums.LoginTypeEnums;
import com.xht.framework.core.enums.UserStatusEnums;
import com.xht.framework.core.enums.UserTypeEnums;
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
import com.xht.framework.security.constant.SecurityConstant;
import com.xht.framework.security.core.userdetails.BasicUserDetails;
import com.xht.system.modules.authority.common.enums.MenuCommonStatus;
import com.xht.system.modules.authority.dao.SysRoleMenuDao;
import com.xht.system.modules.authority.dao.SysUserRoleDao;
import com.xht.system.modules.authority.domain.entity.SysRoleEntity;
import com.xht.system.modules.authority.domain.response.RouterMetaResponse;
import com.xht.system.modules.authority.domain.response.SysMenuResponse;
import com.xht.system.modules.authority.domain.vo.RouterVo;
import com.xht.system.modules.dept.domain.response.SysPostResponse;
import com.xht.system.modules.user.SysUserHelper;
import com.xht.system.modules.user.converter.SysUserConverter;
import com.xht.system.modules.user.dao.SysUserDao;
import com.xht.system.modules.user.dao.SysUserDetailDao;
import com.xht.system.modules.user.dao.SysUserPostDao;
import com.xht.system.modules.user.domain.entity.SysUserDetailEntity;
import com.xht.system.modules.user.domain.entity.SysUserEntity;
import com.xht.system.modules.user.domain.form.SysUserDetailForm;
import com.xht.system.modules.user.domain.form.SysUserForm;
import com.xht.system.modules.user.domain.form.UpdatePwdFrom;
import com.xht.system.modules.user.domain.query.SysUserQuery;
import com.xht.system.modules.user.domain.response.SysUserDetailResponse;
import com.xht.system.modules.user.domain.response.SysUserResponse;
import com.xht.system.modules.user.domain.vo.SysUserVO;
import com.xht.system.modules.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
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

    private final PasswordEncoder passwordEncoder;

    private final RedisService redisService;


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
     * 注册手机用户
     *
     * @param phone 手机号
     * @return 注册用户信息
     */
    @Override
    public UserInfoDTO registerPhoneUser(String phone) {
        String key = Keys.createKey(SecurityConstant.USER_REGISTER_PHONE_KEY_PREFIX, phone);
        Long expire = redisService.getExpire(key);
        if (expire < 0) {
            throw new BusinessException("改手机号无法注册用户，请稍后重试!");
        }
        boolean userPhoneExists = sysUserDao.checkUserPhoneExists(phone, null);
        ThrowUtils.throwIf(userPhoneExists, BusinessErrorCode.DATA_EXIST, "该手机号已注册过账号，若为本人操作，可直接登录或联系客服核实！");
        long userId = IdUtil.getSnowflakeNextId();
        SysUserEntity entity = new SysUserEntity();
        entity.setId(userId);
        entity.setUserType(UserTypeEnums.USER);
        entity.setUserName(IdUtil.fastSimpleUUID());
        entity.setNickName(phone);
        entity.setPassWord(passwordEncoder.encode("123456123456"));
        entity.setPassWordSalt("123456");
        entity.setUserStatus(UserStatusEnums.UNACTIVATED);
        entity.setUserPhone(phone);
        entity.setUserAvatar(null);
        entity.setDeptId(null);
        entity.setDeptName(null);
        SysUserDetailEntity detailEntity = new SysUserDetailEntity();
        detailEntity.setUserId(userId);
        sysUserDetailDao.saveUserInfo(entity, detailEntity);
        UserInfoDTO infoDTO = new UserInfoDTO();
        infoDTO.setUserId(userId);
        infoDTO.setUserType(entity.getUserType());
        infoDTO.setUserName(entity.getUserName());
        infoDTO.setNickName(entity.getNickName());
        infoDTO.setPassWord(entity.getPassWord());
        infoDTO.setPassWordSalt(entity.getPassWordSalt());
        infoDTO.setUserStatus(entity.getUserStatus());
        infoDTO.setUserPhone(phone);
        infoDTO.setRegisterDate(LocalDateTime.now());
        return infoDTO;
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
    public UserInfoDTO loadUserByUsername(String username, LoginTypeEnums loginType) {
        if (Objects.isNull(loginType) || Objects.equals(loginType, LoginTypeEnums.WECHAT) || Objects.equals(loginType, LoginTypeEnums.QQ)) {
            throw new BusinessException("用户名或密码错误.");
        }
        UserInfoDTO userInfoDTO = sysUserDao.findByUsernameAndLoginType(username, loginType);
        if (Objects.isNull(userInfoDTO)) {
            throw new BusinessException("用户名或密码错误.");
        }
        List<SysRoleEntity> roles = sysUserRoleDao.findRoleListByUserId(userInfoDTO.getUserId());
        Set<String> menuButtonCodes = sysRoleMenuDao.findPermissionCodeByUserId(userInfoDTO.getUserId());
        if (!CollectionUtils.isEmpty(roles)) {
            userInfoDTO.setRoleCodes(roles.stream().map(SysRoleEntity::getRoleCode).collect(Collectors.toSet()));
        }
        if (!CollectionUtils.isEmpty(menuButtonCodes)) {
            userInfoDTO.setMenuButtonCodes(menuButtonCodes);
        }
        return userInfoDTO;
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
            RouterVo routerVo = new RouterVo();
            routerVo.setPath(menu.getMenuPath());
            routerVo.setName(StringUtils.emptyToDefault(menu.getViewName(), menu.getId() + ""));
            routerVo.setComponent(menu.getViewPath());
            routerVo.setMeta(getMetaVo(menu));
            result.add(new TreeNode<>(menu.getId(), menu.getParentId(), menu.getMenuSort()).setExtra(BeanUtil.beanToMap(routerVo)));
        }
        return TreeUtils.buildList(result, Boolean.FALSE);
    }

    /**
     * 验证手机号是否存在
     *
     * @param phone 手机号
     * @return 是否存在
     */
    @Override
    public Boolean checkPhoneExists(String phone) {
        Boolean exists = sysUserDao.exists(SysUserEntity::getUserPhone, phone);
        if (!exists) {
            redisService.set(Keys.createKey(SecurityConstant.USER_REGISTER_PHONE_KEY_PREFIX, phone), phone, SecurityConstant.CAPTCHA_EXPIRE_TIME, TimeUnit.SECONDS);
        }
        return exists;
    }


    /**
     * 获取菜单元数据
     *
     * @param menu 菜单信息
     * @return 菜单元数据
     */
    private static RouterMetaResponse getMetaVo(SysMenuResponse menu) {
        RouterMetaResponse routerMetaResponse = new RouterMetaResponse();
        routerMetaResponse.setTitle(menu.getMenuName());
        routerMetaResponse.setIcon(menu.getMenuIcon());
        routerMetaResponse.setLinkStatus(Objects.requireNonNullElse(menu.getFrameFlag(), MenuCommonStatus.NO).getStatus());
        routerMetaResponse.setMenuType(menu.getMenuType().getValue());
        routerMetaResponse.setAffixStatus(Objects.requireNonNullElse(menu.getAffixStatus(), MenuCommonStatus.NO).getStatus());
        routerMetaResponse.setActiveMenuPath(menu.getActiveMenuPath());
        routerMetaResponse.setHiddenStatus(Objects.requireNonNullElse(menu.getMenuHidden(), MenuCommonStatus.YES).getStatus());
        routerMetaResponse.setKeepAliveStatus(Objects.requireNonNullElse(menu.getMenuCache(), MenuCommonStatus.YES).getStatus());
        routerMetaResponse.setRoles(StrUtil.splitTrim(menu.getMenuAuthority(), ","));//perms
        routerMetaResponse.setRank(menu.getMenuSort());
        return routerMetaResponse;
    }
}
