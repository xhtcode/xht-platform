package com.xht.platform.common.file.bo;

import com.xht.framework.common.domain.bo.XhtBO;
import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.utils.AssertUtils;
import com.xht.framework.utils.StringUtils;
import com.xht.platform.common.file.constant.SysFileConstant;
import com.xht.platform.common.file.utils.SysFileUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.file.Path;

/**
 * 上传文件参数BO
 * 封装文件上传所需入参，使用建造者模式构建，统一参数校验与路径前缀清洗
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UploadBO<T> implements XhtBO {

    /**
     * 文件对象，泛型支持MultipartFile/字节数组等多种文件类型
     */
    private final T requestFile;

    /**
     * 文件用途标识，用于区分不同业务模块上传的文件（如：合同、附件、头像等）
     */
    private final String fileUsed;

    /**
     * 文件路径前缀，文件存储根路径，会自动清洗开头的路径分隔符 / \
     */
    private final String filePathPrefix;

    /**
     * 文件描述
     */
    private final String fileDesc;

    /**
     * 文件上传用户
     */
    private final String fileUploadUser;

    /**
     * 构建UploadBo，必须传入文件对象作为必填项
     *
     * @param requestFile 文件对象
     * @param <T>         文件泛型
     * @return 建造器
     */
    public static <T> UploadBoBuilder<T> builder(T requestFile) {
        return new UploadBoBuilder<>(requestFile);
    }

    /**
     * 上传文件参数建造器
     * 链式赋值、参数校验、路径清洗，最终生成不可变UploadBo对象
     *
     * @param <T> 文件类型泛型
     */
    public static class UploadBoBuilder<T> {
        /**
         * 文件对象
         */
        private final T requestFile;

        /**
         * 文件用途
         */
        private String fileUsed;

        /**
         * 文件路径前缀
         */
        private String filePathPrefix;

        /**
         * 文件描述
         */
        private String fileDesc;

        /**
         * 文件上传用户
         */
        private String fileUploadUser;

        /**
         * 私有构造，由builder方法入口实例化
         *
         * @param requestFile 文件对象
         */
        private UploadBoBuilder(T requestFile) {
            // 参数校验：文件对象非空、存储路径前缀非空
            AssertUtils.notNull(requestFile, BusinessErrorCode.FILE_UPLOAD_EMPTY);
            this.requestFile = requestFile;
        }

        /**
         * 设置文件用途标识
         *
         * @param fileUsed 文件用途
         * @return 当前建造器
         */
        public UploadBoBuilder<T> fileUsed(final String fileUsed) {
            this.fileUsed = fileUsed;
            return this;
        }

        /**
         * 设置文件路径前缀
         * <p>非空校验，并使用正则清洗前缀开头的 / \ 分隔符</p>
         *
         * @param filePathPrefix 文件存储根路径前缀
         * @return 当前建造器
         */
        public UploadBoBuilder<T> filePathPrefix(final String filePathPrefix) {
            AssertUtils.hasText(filePathPrefix, BusinessErrorCode.FILE_PATH_ILLEGAL);
            this.filePathPrefix = StringUtils.trimAllWhitespace(filePathPrefix);
            return this;
        }

        /**
         * 设置文件描述
         *
         * @param fileDesc 文件描述
         * @return 当前建造器
         */
        public UploadBoBuilder<T> fileDesc(final String fileDesc) {
            this.fileDesc = fileDesc;
            return this;
        }

        /**
         * 设置文件上传用户
         *
         * @param fileUploadUser 文件上传用户
         * @return 当前建造器
         */
        public UploadBoBuilder<T> fileUploadUser(final String fileUploadUser) {
            AssertUtils.hasText(fileUploadUser, BusinessErrorCode.FILE_UPLOAD_USER_ILLEGAL);
            this.fileUploadUser = fileUploadUser;
            return this;
        }

        /**
         * 执行参数校验，生成最终UploadBo实例
         * <p>校验规则：文件不能为空、路径前缀不能为空；
         * 路径前缀为空时自动生成默认存储路径</p>
         *
         * @return 校验完成的UploadBo
         */
        public UploadBO<T> build() {
            AssertUtils.hasText(fileUploadUser, BusinessErrorCode.FILE_UPLOAD_USER_ILLEGAL);
            // 兜底：前缀为空则生成默认文件存储路径
            this.filePathPrefix = StringUtils.emptyToDefault(filePathPrefix, SysFileUtils.generateDefaultFilePath());
            String normalize = Path.of(this.filePathPrefix).normalize().toString();
            AssertUtils.isTrue(!normalize.contains(SysFileConstant.PATH_POINT), BusinessErrorCode.FILE_PATH_ILLEGAL);
            return new UploadBO<>(this.requestFile, this.fileUsed, normalize, this.fileDesc, this.fileUploadUser);
        }
    }

}
