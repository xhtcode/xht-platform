package com.xht.framework.core.utils.file;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.xht.framework.core.exception.utils.ThrowUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static com.xht.framework.core.constant.StringConstant.EMPTY;
import static com.xht.framework.core.constant.StringConstant.POINT;

/**
 * 文件转换工具类，提供文件与Base64、字节数组、输入流等格式的相互转换功能
 *
 * @author xht
 **/
@SuppressWarnings("unused")
public final class FileUtils {

    /**
     * 缓冲区大小，用于文件流读写
     */
    private static final int BUFFER_SIZE = 4096;

    /**
     * 私有构造方法，防止工具类被实例化
     */
    private FileUtils() {
        throw new AssertionError("工具类不允许实例化");
    }

    /**
     * 生成规范的文件名（前缀.后缀）
     *
     * @param prefix 文件名前缀（不能为空）
     * @param suffix 文件名后缀（不能为空，无需带".", 如"txt"而非".txt"）
     * @return 组合后的文件名，格式为"prefix.suffix"
     */
    public static String generateFileName(String prefix, String suffix) {
        ThrowUtils.hasText(prefix, "文件前缀不能为空");
        ThrowUtils.hasText(suffix, "文件后缀不能为空");
        // 移除后缀可能带的".", 避免生成"prefix..suffix"
        return String.format("%s.%s", StrUtil.replaceLast(prefix, POINT, EMPTY), StrUtil.replaceFirst(suffix, POINT, EMPTY));
    }


    /**
     * 将文件转换为Base64字符串
     *
     * @param file 文件对象（不能为空）
     * @return 文件的Base64编码字符串
     */
    public static String getBase64(File file) {
        ThrowUtils.notNull(file, "文件对象不能为空");
        return Base64.encode(file);
    }

    /**
     * 将字节数组转换为Base64字符串
     *
     * @param bytes 文件字节数组（不能为空）
     * @return 字节数组的Base64编码字符串
     */
    public static String getBase64(byte[] bytes) {
        ThrowUtils.notNull(bytes, "字节数组不能为空");
        return Base64.encode(bytes);
    }

    /**
     * 将输入流转换为Base64字符串
     *
     * @param inputStream 文件输入流（不能为空）
     * @return 输入流内容的Base64编码字符串
     */
    public static String getBase64(InputStream inputStream) {
        ThrowUtils.notNull(inputStream, "输入流不能为空");
        return Base64.encode(inputStream);
    }

    /**
     * 将文件转换为字节数组
     *
     * @param file 文件对象（不能为空）
     * @return 文件内容的字节数组
     */
    public static byte[] getByte(File file) {
        ThrowUtils.notNull(file, "文件对象不能为空");
        return FileUtil.readBytes(file);
    }

    /**
     * 将Base64字符串转换为字节数组
     *
     * @param base64 Base64编码字符串（不能为空）
     * @return 解码后的字节数组
     */
    public static byte[] getByte(String base64) {
        ThrowUtils.hasText(base64, "Base64字符串不能为空");
        return Base64.decode(base64);
    }

    /**
     * 将输入流转换为字节数组
     *
     * @param inputStream 文件输入流（不能为空）
     * @return 输入流内容的字节数组
     */
    public static byte[] getByte(InputStream inputStream) {
        ThrowUtils.notNull(inputStream, "输入流不能为空");
        return IoUtil.readBytes(inputStream);
    }

    /**
     * 将文件转换为输入流
     *
     * @param file 文件对象（不能为空）
     * @return 文件对应的输入流
     */
    public static InputStream getInputStream(File file) {
        ThrowUtils.notNull(file, "文件对象不能为空");
        return IoUtil.toStream(file);
    }

    /**
     * 将Base64字符串转换为输入流
     *
     * @param base64 Base64编码字符串（不能为空）
     * @return 解码后的内容对应的输入流
     */
    public static InputStream getInputStream(String base64) {
        ThrowUtils.hasText(base64, "Base64字符串不能为空");
        byte[] decode = Base64.decode(base64);
        return IoUtil.toStream(decode);
    }

    /**
     * 将字节数组转换为输入流
     *
     * @param bytes 文件字节数组（不能为空）
     * @return 字节数组对应的输入流
     */
    public static InputStream getInputStream(byte[] bytes) {
        ThrowUtils.notNull(bytes, "字节数组不能为空");
        return IoUtil.toStream(bytes);
    }

    /**
     * 将MultipartFile转换为File
     *
     * @param multipartFile 前端上传的文件（不能为空）
     * @param filePath      目标文件路径（包含文件名，如"/data/file.txt"）
     * @return 转换后的File对象
     * @throws IOException 当文件操作失败时抛出（如目录不存在、权限不足等）
     */
    public static File getFile(MultipartFile multipartFile, String filePath) throws IOException {
        // 校验输入参数
        ThrowUtils.notNull(multipartFile, "上传文件不能为空");
        ThrowUtils.hasText(filePath, "文件路径不能为空");
        if (multipartFile.isEmpty()) {
            throw new IOException("上传文件内容为空");
        }

        // 创建文件对象并确保父目录存在
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            boolean mkDirs = parentDir.mkdirs();
            if (!mkDirs) {
                throw new IOException("创建父目录失败: " + parentDir.getAbsolutePath());
            }
        }

        // 拷贝流数据到文件（使用hutool工具类简化代码）
        try (InputStream inputStream = multipartFile.getInputStream()) {
            FileUtil.writeFromStream(inputStream, file);
        }

        return file;
    }

}
