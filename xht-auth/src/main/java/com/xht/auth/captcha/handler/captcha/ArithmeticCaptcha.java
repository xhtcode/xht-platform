package com.xht.auth.captcha.handler.captcha;

import com.xht.auth.captcha.handler.AbstractCaptcha;
import com.xht.framework.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Objects;

/**
 * 算术验证码
 *
 * @author xht
 **/
@Slf4j
public class ArithmeticCaptcha extends AbstractCaptcha {

    /**
     * 计算公式
     */
    private String arithmetic;

    public ArithmeticCaptcha(int width, int height) {
        super(width, height);
    }

    /**
     * 生成验证码
     */
    @Override
    public void generate() {
        String[] result = StringUtils.split(ArithmeticProblemUtil.create(), "@");
        if (Objects.nonNull(result) && result.length == 2) {
            this.arithmetic = result[0];
            this.code = result[1];
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
    @Override
    protected void graphicsImage(ByteArrayOutputStream outputStream) throws Exception {
        char[] arithmeticCharArray = this.arithmetic.toCharArray();
        BufferedImage bi = new BufferedImage(super.width, super.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = (Graphics2D) bi.getGraphics();
        // 启用抗锯齿，使文字更平滑
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 填充背景
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        // 画干扰圆
        drawOval(g2d);
        // 画字符串
        g2d.setFont(super.font);
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int fW = width / arithmeticCharArray.length;  // 每一个字符所占的宽度
        int fSp = (fW - (int) fontMetrics.getStringBounds("8", g2d).getWidth()) / 2;  // 字符的左右边距
        for (int i = 0; i < arithmeticCharArray.length; i++) {
            g2d.setColor(color());
            int fY = height - ((height - (int) fontMetrics.getStringBounds(String.valueOf(arithmeticCharArray[i]), g2d).getHeight()) >> 1);  // 文字的纵坐标
            g2d.drawString(String.valueOf(arithmeticCharArray[i]), i * fW + fSp + 3, fY - 3);
        }
        g2d.dispose();
        ImageIO.write(bi, "png", outputStream);
        outputStream.flush();
    }

    /**
     * 获取算术验证码字符串
     * <p>
     * 获取算术验证码字符串前先检查验证码是否已生成，如果未生成则先生成验证码
     * </p>
     *
     * @return 算术验证码字符串
     */
    public String getArithmetic() {
        checkGenerate();
        return arithmetic;
    }

}
