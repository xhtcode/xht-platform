package com.xht.platform.file.convert;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * 描述： LibreOffice Office 转 PDF
 * <p>使用独立 UserInstallation 目录，禁用宏与恢复，源文件只读</p>
 *
 * @author xht
 **/
@Slf4j
@Component
public class OfficeToPdfConverter implements FileConverter {

    /**
     * 支持的 Office 后缀
     */
    private static final List<String> OFFICE_SUFFIXES = List.of(
            "doc", "docx", "rtf", "odt", "xls", "xlsx", "ods", "ppt", "pptx", "odp");

    @Value("${xht.file.convert.libreoffice-path:soffice}")
    private String libreOfficePath;

    @Value("${xht.file.convert.timeout-seconds:120}")
    private long timeoutSeconds;

    @Override
    public boolean supports(String sourceSuffix, String targetFormat) {
        return "pdf".equalsIgnoreCase(targetFormat)
                && sourceSuffix != null
                && OFFICE_SUFFIXES.contains(sourceSuffix.toLowerCase(Locale.ROOT));
    }

    @Override
    public void convert(Path source, String sourceSuffix, Path target, String targetFormat) throws Exception {
        Path workDir = Files.createTempDirectory("xht-office-");
        Path profileDir = workDir.resolve("profile");
        Files.createDirectories(profileDir);
        try {
            List<String> command = new ArrayList<>();
            command.add(libreOfficePath);
            command.add("--headless");
            command.add("--norestore");
            command.add("--nolockcheck");
            command.add("--nofirststartwizard");
            command.add("-env:UserInstallation=" + profileDir.toUri());
            command.add("--convert-to");
            command.add("pdf");
            command.add("--outdir");
            command.add(workDir.toString());
            command.add(source.toString());

            Process process = new ProcessBuilder(command)
                    .redirectErrorStream(true)
                    .start();
            boolean finished = process.waitFor(timeoutSeconds, TimeUnit.SECONDS);
            if (!finished) {
                process.destroyForcibly();
                throw new IOException("LibreOffice 转换超时");
            }
            if (process.exitValue() != 0) {
                throw new IOException("LibreOffice 转换失败，退出码 " + process.exitValue());
            }
            String baseName = source.getFileName().toString();
            int dot = baseName.lastIndexOf('.');
            if (dot > 0) {
                baseName = baseName.substring(0, dot);
            }
            Path converted = workDir.resolve(baseName + ".pdf");
            if (!Files.exists(converted)) {
                throw new IOException("LibreOffice 未生成 PDF 文件");
            }
            Files.createDirectories(target.getParent());
            Files.copy(converted, target, StandardCopyOption.REPLACE_EXISTING);
        } finally {
            deleteQuietly(workDir);
        }
    }

    /**
     * 静默删除临时目录
     *
     * @param path 目录
     */
    private void deleteQuietly(Path path) {
        try {
            if (path == null || !Files.exists(path)) {
                return;
            }
            try (var stream = Files.walk(path)) {
                stream.sorted((a, b) -> b.getNameCount() - a.getNameCount())
                        .forEach(p -> {
                            try {
                                Files.deleteIfExists(p);
                            } catch (IOException ignored) {
                                // 临时目录清理失败不影响主流程
                            }
                        });
            }
        } catch (IOException e) {
            log.warn("清理 Office 转换临时目录失败：{}", path, e);
        }
    }

}
