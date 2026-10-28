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
 * 分片合并参数BO
 * 封装文件分片合并所需入参，使用建造者模式构建，统一参数校验与路径前缀清洗
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChunkMergeBO implements XhtBO {

    /**
     * 分片上传标识，同一文件的所有分片共用，与分片上传时保持一致
     */
    private final String uploadId;

    /**
     * 原始文件名（含后缀），合并后作为文件记录的原始名称
     */
    private final String fileName;

    /**
     * 分片总数，服务端按 0 ~ chunkTotal-1 校验分片完整性
     */
    private final Integer chunkTotal;

    /**
     * 文件用途标识，用于区分不同业务模块上传的文件（如：合同、附件、头像等）
     */
    private final String fileUsed;

    /**
     * 文件描述
     */
    private final String fileDesc;

    /**
     * 文件整体MD5，非空时服务端在合并完成后校验，不一致抛 FILE_CHUNK_VERIFY_FAIL
     */
    private final String fileHash;

    /**
     * 文件上传用户
     */
    private final String fileUploadUser;

    /**
     * 文件路径前缀，文件存储根路径，会自动清洗开头的路径分隔符 / \
     */
    private final String filePathPrefix;

    /**
     * 构建ChunkMergeBO建造器
     *
     * @return 建造器
     */
    public static ChunkMergeBoBuilder builder() {
        return new ChunkMergeBoBuilder();
    }

    /**
     * 分片合并参数建造器
     * 链式赋值、参数校验、路径清洗，最终生成不可变ChunkMergeBO对象
     */
    public static class ChunkMergeBoBuilder {
        /**
         * 分片上传标识
         */
        private String uploadId;

        /**
         * 原始文件名
         */
        private String fileName;

        /**
         * 分片总数
         */
        private Integer chunkTotal;

        /**
         * 文件用途
         */
        private String fileUsed;

        /**
         * 文件描述
         */
        private String fileDesc;

        /**
         * 文件整体MD5
         */
        private String fileHash;

        /**
         * 文件上传用户
         */
        private String fileUploadUser;

        /**
         * 文件路径前缀
         */
        private String filePathPrefix;

        /**
         * 设置分片上传标识
         *
         * @param uploadId 分片上传标识
         * @return 当前建造器
         */
        public ChunkMergeBoBuilder uploadId(final String uploadId) {
            this.uploadId = uploadId;
            return this;
        }

        /**
         * 设置原始文件名
         *
         * @param fileName 原始文件名（含后缀）
         * @return 当前建造器
         */
        public ChunkMergeBoBuilder fileName(final String fileName) {
            this.fileName = fileName;
            return this;
        }

        /**
         * 设置分片总数
         *
         * @param chunkTotal 分片总数
         * @return 当前建造器
         */
        public ChunkMergeBoBuilder chunkTotal(final Integer chunkTotal) {
            this.chunkTotal = chunkTotal;
            return this;
        }

        /**
         * 设置文件用途标识
         *
         * @param fileUsed 文件用途
         * @return 当前建造器
         */
        public ChunkMergeBoBuilder fileUsed(final String fileUsed) {
            this.fileUsed = fileUsed;
            return this;
        }

        /**
         * 设置文件描述
         *
         * @param fileDesc 文件描述
         * @return 当前建造器
         */
        public ChunkMergeBoBuilder fileDesc(final String fileDesc) {
            this.fileDesc = fileDesc;
            return this;
        }

        /**
         * 设置文件整体MD5（可选，用于合并后完整性校验）
         *
         * @param fileHash 文件整体MD5
         * @return 当前建造器
         */
        public ChunkMergeBoBuilder fileHash(final String fileHash) {
            this.fileHash = fileHash;
            return this;
        }

        /**
         * 设置文件上传用户
         *
         * @param fileUploadUser 文件上传用户
         * @return 当前建造器
         */
        public ChunkMergeBoBuilder fileUploadUser(final String fileUploadUser) {
            this.fileUploadUser = fileUploadUser;
            return this;
        }

        /**
         * 设置文件路径前缀
         * <p>非空校验，并使用正则清洗前缀开头的 / \ 分隔符</p>
         *
         * @param filePathPrefix 文件存储根路径前缀
         * @return 当前建造器
         */
        public ChunkMergeBoBuilder filePathPrefix(final String filePathPrefix) {
            AssertUtils.hasText(filePathPrefix, BusinessErrorCode.FILE_PATH_ILLEGAL);
            this.filePathPrefix = StringUtils.trimAllWhitespace(filePathPrefix);
            return this;
        }

        /**
         * 执行参数校验，生成最终ChunkMergeBO实例
         * <p>校验规则：上传标识格式合法、文件名非空、分片总数大于0、上传用户非空；
         * 路径前缀为空时自动生成默认存储路径</p>
         *
         * @return 校验完成的ChunkMergeBO
         */
        public ChunkMergeBO build() {
            AssertUtils.hasText(this.uploadId, BusinessErrorCode.PARAM_ERROR, "分片上传标识不能为空");
            AssertUtils.isTrue(this.uploadId.matches(SysFileConstant.UPLOAD_ID_PATTERN), BusinessErrorCode.PARAM_ERROR, "分片上传标识不合法");
            AssertUtils.hasText(this.fileName, BusinessErrorCode.PARAM_ERROR, "文件名不能为空");
            AssertUtils.notNull(this.chunkTotal, BusinessErrorCode.PARAM_ERROR, "分片总数不能为空");
            AssertUtils.isTrue(this.chunkTotal > 0, BusinessErrorCode.PARAM_ERROR, "分片总数必须大于0");
            AssertUtils.hasText(this.fileUploadUser, BusinessErrorCode.FILE_UPLOAD_USER_ILLEGAL);
            // 兜底：前缀为空则生成默认文件存储路径
            this.filePathPrefix = StringUtils.emptyToDefault(this.filePathPrefix, SysFileUtils.generateDefaultFilePath());
            String normalize = Path.of(this.filePathPrefix).normalize().toString();
            AssertUtils.isTrue(!normalize.contains(SysFileConstant.PATH_POINT), BusinessErrorCode.FILE_PATH_ILLEGAL);
            return new ChunkMergeBO(this.uploadId, this.fileName, this.chunkTotal, this.fileUsed, this.fileDesc, this.fileHash, this.fileUploadUser, normalize);
        }
    }

}
