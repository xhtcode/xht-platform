package com.xht.platform.user;

import com.xht.framework.utils.tree.INode;
import com.xht.platform.login.domain.response.UserLoginLogResponse;
import com.xht.platform.system.domain.vo.SysUserVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 描述： 用户信息接口
 *
 * @author xht
 **/
public interface IUserProfileService {

    /**
     * 上传用户头像
     *
     * @param request HTTP请求对象
     * @param avatar  头像文件，支持jpg、png等图片格式
     * @return 上传成功后返回文件预览访问地址
     */
    String uploadUserAvatar(HttpServletRequest request, MultipartFile avatar);

    /**
     * 获取当前登录的用户信息
     *
     * @return 用户信息
     */
    SysUserVO getUserProfileInfo();

    /**
     * 获取当前登录用户的路由信息
     *
     * @return 路由信息
     */
    List<INode<Long>> getUserProfileRouters();

    /**
     * 获取当前登录用户登录日志
     *
     * @return 登录日志信息
     */
    List<UserLoginLogResponse> getUserProfileLoginLog();

}
