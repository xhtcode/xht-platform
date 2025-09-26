package com.xht.auth.captcha.handler;

import cn.hutool.core.util.RandomUtil;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.core.utils.file.ResourceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;

import javax.xml.catalog.CatalogException;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Base64;

/**
 * 抽象验证码处理类
 * <p>
 * 该类定义了验证码处理的基本框架，包括验证码的生成检查、Base64编码转换等通用功能。
 * 子类需要实现具体的验证码生成逻辑。
 * </p>
 *
 * @author xht
 **/
@Slf4j
@SuppressWarnings("all")
public abstract class AbstractCaptcha {

    /**
     * 验证码字体常用颜色
     */
    private static final int[][] COLOR = {
            {0, 85, 204},    // 深蓝色
            {0, 128, 0},     // 深绿色
            {204, 0, 0},     // 深红色
            {255, 102, 0},   // 橙色
            {139, 69, 19},   // 深棕色
            {128, 0, 128},   // 深紫色
            {0, 139, 139},   // 深青色
            {65, 105, 225},  // 皇家蓝
            {0, 70, 130},    // 藏青色
            {184, 40, 40},   // 暗红色
            {0, 112, 192},   // 钢青色
            {0, 51, 102}     // 靛蓝色
    };

    /**
     * 验证码的code
     */
    protected String code;

    /**
     * 验证码图片宽度
     */
    protected final int width;

    /**
     * 验证码图片高度
     */
    protected final int height;

    /**
     * 验证码使用的字体
     */
    protected Font font;

    /**
     * 背景色
     */
    protected Color background;

    /**
     * 构造函数，初始化验证码图片的宽度和高度
     *
     * @param width  验证码宽度
     * @param height 验证码高度
     */
    public AbstractCaptcha(int width, int height) {
        this.width = width;
        this.height = height;
        Resource resource = ResourceUtils.getResource("font/actionj.ttf");
        try (InputStream inputStream = resource.getInputStream()) {
            Font baseFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            this.font = baseFont.deriveFont(Font.BOLD, (int) (this.height * 0.75));
        } catch (FileNotFoundException e) {
            throw new CatalogException("字体文件不存在");
        } catch (Exception e) {
            log.error("字体创建失败:{}", e.getMessage(), e);
            throw new CatalogException("字体创建失败");
        }
        this.background = Color.WHITE;
        ThrowUtils.throwIf(width <= 0, "验证码宽度不能小于等于0");
        ThrowUtils.throwIf(height <= 0, "验证码高度不能小于等于0");
    }

    /**
     * 生成验证码
     */
    public abstract void generate();


    /**
     * 检查并生成验证码
     * <p>
     * 检查验证码是否已生成，如果未生成则调用generate方法进行生成
     * </p>
     */
    protected final void checkGenerate() {
        if (!StringUtils.hasText(code)) {
            generate();
        }
    }

    /**
     * 将验证码转换为Base64字符串
     *
     * @return 验证码的Base64编码字符串
     */
    public final String getBase64() {
        try {
            checkGenerate();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            graphicsImage(outputStream);
            return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (Exception e) {
            log.error("验证码生成失败:{}", e.getMessage(), e);
            throw new CatalogException("验证码生成失败");
        }
    }

    /**
     * 将验证码图形绘制到输出流中
     * <p>
     * 抽象方法，由子类实现具体的图形绘制逻辑，将验证码以图像形式写入到输出流中
     * </p>
     *
     * @param outputStream 字节输出流，用于存储验证码图像数据
     */
    protected abstract void graphicsImage(ByteArrayOutputStream outputStream) throws Exception;

    /**
     * 将验证码转换为指定类型的Base64字符串
     * <p>
     * 在验证码Base64字符串前添加类型前缀
     * </p>
     *
     * @param type 类型前缀字符串
     * @return 带有类型前缀的验证码Base64编码字符串
     */
    public final String getBase64(String type) {
        return String.format(type, getBase64());
    }

    /**
     * 获取验证码
     * <p>
     * 获取验证码前先检查验证码是否已生成，如果未生成则先生成验证码
     * </p>
     *
     * @return 验证码字符串
     */
    public final String getCode() {
        checkGenerate();
        return code;
    }

    /**
     * 设置字体
     *
     * @param font 字体
     */
    public void setFont(Font font) {
        ThrowUtils.notNull(font, "验证码字体不能为空");
        this.font = font;
    }

    /**
     * 设置背景色
     *
     * @param background 背景色
     */
    public void setBackground(Color background) {
        ThrowUtils.notNull(background, "验证码背景色不能为空");
        this.background = background;
    }

    /**
     * 随机画干扰圆
     *
     * @param num 数量
     * @param g   Graphics2D
     */
    protected void drawOval(Graphics2D g) {
        int count = RandomUtil.randomInt(2, 10);
        for (int i = 0; i < count; i++) {
            g.setColor(color());
            int w = 5;
            g.drawOval(RandomUtil.randomInt(0, width), RandomUtil.randomInt(0, height), w, w);
        }
    }

    /**
     * 获取随机常用颜色
     *
     * @return 随机颜色
     */
    protected Color color() {
        int[] color = COLOR[RandomUtil.randomInt(0, COLOR.length - 1)];
        return new Color(color[0], color[1], color[2]);
    }


}
