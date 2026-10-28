package com.xht.platform.common.file.bo;

import com.xht.framework.common.domain.bo.XhtBO;
import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.utils.AssertUtils;
import com.xht.platform.common.file.constant.SysFileConstant;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 分片上传参数BO
 * 封装单个文件分片上传所需入参，使用建造者模式构建，统一参数校验
 * <p>
 * 分片存储位置由服务端按约定生成：{chunks}/{uploadId}/{chunkIndex}.part，调用方无需传入
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChunkUploadBO<T> implements XhtBO {

    /**
     * 分片文件对象，泛型支持MultipartFile/字节数组等多种文件类型
     */
    private final T requestFile;

    /**
     * 分片上传标识，同一文件的所有分片共用，推荐使用文件MD5或UUID
     */
    private final String uploadId;

    /**
     * 分片序号，从0开始
     */
    private final Integer chunkIndex;

    /**
     * 文件上传用户
     */
    private final String fileUploadUser;

    /**
     * 构建ChunkUploadBo，必须传入分片文件对象作为必填项
     *
     * @param requestFile 分片文件对象
     * @param <T>         文件泛型
     * @return 建造器
     */
    public static <T> ChunkUploadBoBuilder<T> builder(T requestFile) {
        return new ChunkUploadBoBuilder<>(requestFile);
    }

    /**
     * 分片上传参数建造器
     * 链式赋值、参数校验，最终生成不可变ChunkUploadBO对象
     *
     * @param <T> 文件类型泛型
     */
    public static class ChunkUploadBoBuilder<T> {
        /**
         * 分片文件对象
         */
        private final T requestFile;

        /**
         * 分片上传标识
         */
        private String uploadId;

        /**
         * 分片序号
         */
        private Integer chunkIndex;

        /**
         * 文件上传用户
         */
        private String fileUploadUser;

        /**
         * 私有构造，由builder方法入口实例化
         *
         * @param requestFile 分片文件对象
         */
        private ChunkUploadBoBuilder(T requestFile) {
            // 参数校验：分片文件对象非空
            AssertUtils.notNull(requestFile, BusinessErrorCode.FILE_UPLOAD_EMPTY);
            this.requestFile = requestFile;
        }

        /**
         * 设置分片上传标识
         *
         * @param uploadId 分片上传标识
         * @return 当前建造器
         */
        public ChunkUploadBoBuilder<T> uploadId(final String uploadId) {
            this.uploadId = uploadId;
            return this;
        }

        /**
         * 设置分片序号
         *
         * @param chunkIndex 分片序号，从0开始
         * @return 当前建造器
         */
        public ChunkUploadBoBuilder<T> chunkIndex(final Integer chunkIndex) {
            this.chunkIndex = chunkIndex;
            return this;
        }

        /**
         * 设置文件上传用户
         *
         * @param fileUploadUser 文件上传用户
         * @return 当前建造器
         */
        public ChunkUploadBoBuilder<T> fileUploadUser(final String fileUploadUser) {
            this.fileUploadUser = fileUploadUser;
            return this;
        }

        /**
         * 执行参数校验，生成最终ChunkUploadBO实例
         * <p>校验规则：分片文件不能为空、上传标识非空且格式合法、分片序号非空且不小于0、上传用户非空</p>
         *
         * @return 校验完成的ChunkUploadBO
         */
        public ChunkUploadBO<T> build() {
            AssertUtils.hasText(this.uploadId, BusinessErrorCode.PARAM_ERROR, "分片上传标识不能为空");
            AssertUtils.isTrue(this.uploadId.matches(SysFileConstant.UPLOAD_ID_PATTERN), BusinessErrorCode.PARAM_ERROR, "分片上传标识不合法");
            AssertUtils.notNull(this.chunkIndex, BusinessErrorCode.PARAM_ERROR, "分片序号不能为空");
            AssertUtils.isTrue(this.chunkIndex >= 0, BusinessErrorCode.PARAM_ERROR, "分片序号不能小于0");
            AssertUtils.hasText(this.fileUploadUser, BusinessErrorCode.FILE_UPLOAD_USER_ILLEGAL);
            return new ChunkUploadBO<>(this.requestFile, this.uploadId, this.chunkIndex, this.fileUploadUser);
        }
    }

}
