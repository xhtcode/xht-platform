package com.xht.generate.domain.bo;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.xht.framework.core.constant.StringConstant;
import com.xht.generate.constant.GenConstant;
import com.xht.generate.constant.enums.GenStatusEnums;
import com.xht.generate.constant.enums.IdPrimaryKeyEnums;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

import static com.xht.generate.constant.GenConstant.COLUMN_NOT_FORM;
import static com.xht.generate.constant.GenConstant.COLUMN_NOT_LIST;

/**
 * 字段业务对象
 *
 * @author xht
 **/
@Getter
public class ColumnBo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 字段名
     */
    @Setter
    private String dbName;

    /**
     * 字段类型
     */
    @Setter
    private String dbType;

    /**
     * 字段注释
     */
    @Setter
    private String dbComment;

    /**
     * 字段长度
     */
    @Setter
    private int dbLength;

    /**
     * 字段必填：0-非必填，1-必填
     */
    private GenStatusEnums dbRequired;

    /**
     * 字段主键：0-非主键，1-主键
     */
    private IdPrimaryKeyEnums dbPrimary;

    /**
     * 字段排序
     */
    @Setter
    private Integer sortOrder;

    public void setDbRequired(int dbRequired) {
        this.dbRequired = GenStatusEnums.of(dbRequired);
    }

    public void setDbPrimary(int dbPrimary) {
        this.dbPrimary = IdPrimaryKeyEnums.of(dbPrimary);
    }


    /**
     * 获取代码类名（表名转驼峰且首字母小写）
     *
     * @return 代码类名
     */
    public String getCodeName() {
        return StrUtil.lowerFirst(StrUtil.toCamelCase(this.dbName));
    }

    /**
     * 获取代码注释（优先使用表注释，无表注释时用表名转中文提示）
     * 示例：tableComment=用户表 → codeComment=用户表；tableComment=null → codeComment=sys_user表
     *
     * @return 代码注释
     */
    public String getCodeComment() {
        return StrUtil.emptyToDefault(this.getDbComment(), StringConstant.EMPTY);
    }

    /**
     * @return 设置 表单新增
     */
    public GenStatusEnums getFromInsert() {
        return determineIncluded(COLUMN_NOT_FORM);
    }

    /**
     * @return 设置 表单更新
     */
    public GenStatusEnums getFromUpdate() {
        return determineIncluded(COLUMN_NOT_FORM);
    }

    /**
     * @return 设置 表单输入长度
     */
    public Integer getFromLength() {
        return this.dbLength;
    }

    /**
     * @return 设置 表单必填
     */
    public GenStatusEnums getFromFill() {
        return determineIncluded(COLUMN_NOT_FORM);
    }

    /**
     * @return 设置 表单组件
     */
    public String getFromComponent() {
        return GenConstant.INPUT;
    }

    /**
     * @return 设置 列表显示
     */
    public GenStatusEnums getListShow() {
        return determineIncluded(COLUMN_NOT_LIST);
    }

    /**
     * @return 设置 列表描述
     */
    public String getListComment() {
        return this.dbComment;
    }

    /**
     * @return 设置 显示切换禁用
     */
    public GenStatusEnums getListDisabled() {
        return GenStatusEnums.NO;
    }

    /**
     * @return 设置 默认隐藏
     */
    public GenStatusEnums getListHidden() {
        return GenStatusEnums.NO;
    }

    /**
     * 判断列是否包含在特定功能中（是否未被排除）
     *
     * @param excludedColumns 排除的列名数组
     * @return 若列名不在排除数组中则返回YES，否则返回NO
     */
    private GenStatusEnums determineIncluded(String[] excludedColumns) {
        if (StrUtil.isBlank(this.dbName) || ArrayUtil.isEmpty(excludedColumns)) {
            return GenStatusEnums.NO;
        }
        return ArrayUtil.contains(excludedColumns, this.dbName)
                ? GenStatusEnums.NO
                : GenStatusEnums.YES;
    }
}
