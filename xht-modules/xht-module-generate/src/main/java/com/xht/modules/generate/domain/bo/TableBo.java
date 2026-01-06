package com.xht.modules.generate.domain.bo;

import cn.hutool.core.util.StrUtil;
import com.xht.framework.core.constant.StringConstant;
import com.xht.framework.core.utils.StringUtils;
import com.xht.modules.common.constant.GenConstant;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import static com.xht.modules.common.constant.GenConstant.PATH_SEPARATOR;

/**
 * 表信息 业务对象
 *
 * @author xht
 **/
@Data
public class TableBo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 模块名称（默认：demo，可通过外部配置覆盖）
     * 用于代码生成的包路径、模块划分（如：sys、biz、tool）
     */
    private static final String moduleName = "demo";

    /**
     * 表ID
     */
    private Long tableId;

    /**
     * 数据库表名
     */
    private String tableName;

    /**
     * 引擎名称
     */
    private String engineName;
    /**
     * 表注释
     */
    private String tableComment;

    /**
     * 表创建时间
     */
    private LocalDateTime tableCreateTime;

    /**
     * 表更新时间
     */
    private LocalDateTime tableUpdateTime;

    /**
     * 获取 模块名称
     *
     * @return 模块名称
     */
    public String getModuleName() {
        return moduleName;
    }


    // ========================= 格式化属性方法 =========================

    /**
     * 获取业务名称（表名的第一个下划线后的部分，小写）
     * 示例：tableName=sys_user → serviceName=use；tableName=order → serviceName=order
     *
     * @return 业务名称
     */
    public String getServiceName() {
        // 从起始位置截取到第一个下划线（无下划线则截取整个表名）
        if (StringUtils.isEmpty(this.tableName)) {
            return StringConstant.EMPTY;
        }
        String[] split = StrUtil.splitToArray(this.tableName, GenConstant.UNDERLINE);
        return split[split.length - 1].toLowerCase();
    }

    /**
     * 获取代码类名（表名转驼峰且首字母大写）
     * 示例：tableName=sys_user → codeName=SysUser；tableName=order_detail → codeName=OrderDetail
     *
     * @return 代码类名
     */
    public String getCodeName() {
        return StrUtil.upperFirst(StrUtil.toCamelCase(this.tableName));
    }

    /**
     * 获取代码注释（优先使用表注释，无表注释时用表名转中文提示）
     * 示例：tableComment=用户表 → codeComment=用户表；tableComment=null → codeComment=sys_user表
     *
     * @return 代码注释（不会返回null）
     */
    public String getCodeComment() {
        if (StringUtils.isEmpty(this.tableComment)) {
            return StringConstant.EMPTY;
        }
        int i = this.tableComment.indexOf("（");
        if (i < 0) {
            i = this.tableComment.indexOf("(");
        }
        if (i < 0) {
            return this.tableComment;
        }
        return this.tableComment.substring(0, i);
    }

    /**
     * 获取请求URL前缀（表名下划线转路径分隔符，且强制以/开头）
     * 示例：tableName=sys_user → urlPrefix=/sys/user；tableName=order → urlPrefix=/order
     *
     * @return URL前缀
     */
    public String getUrlPrefix() {
        String url = StrUtil.replace(tableName, "_", PATH_SEPARATOR);
        return StrUtil.addPrefixIfNot(url, PATH_SEPARATOR);
    }

    /**
     * 获取权限标识前缀（表名下划线转冒号，用于权限控制）
     * 示例：tableName=sys_user → permissionPrefix=sys:user；tableName=order → permissionPrefix=order
     *
     * @return 权限前缀
     */
    public String getPermissionPrefix() {
        return StrUtil.replace(tableName, GenConstant.UNDERLINE, GenConstant.COLON);
    }
}
