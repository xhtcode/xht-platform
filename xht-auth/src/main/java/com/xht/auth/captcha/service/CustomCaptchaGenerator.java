package com.xht.auth.captcha.service;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * 手动实现的验证码生成工具类
 * 支持字符验证码和算术验证码两种模式
 */
@Slf4j
public class CustomCaptchaGenerator {
    // 验证码类型：字符型
    public static final int TYPE_CHAR = 1;
    // 验证码类型：算术型
    public static final int TYPE_ARITHMETIC = 2;

    // 图片宽度
    private int width = 120;
    // 图片高度
    private int height = 40;
    // 字符数量
    private final int charCount = 4;
    // 干扰线数量
    private int lineCount = 5;
    // 干扰点数量
    private int dotCount = 20;
    // 验证码类型
    private int type = TYPE_CHAR;
    // 验证码内容
    private String code;
    // 算术验证码结果
    private int arithmeticResult;

    private final Random random = new Random();
    // 字符集（移除易混淆的字符：0、O、1、I、l）
    private final String charSet = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghjkmnpqrstuvwxyz23456789";

    public CustomCaptchaGenerator() {}

    public CustomCaptchaGenerator(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public CustomCaptchaGenerator(int width, int height, int type) {
        this.width = width;
        this.height = height;
        this.type = type;
    }

    /**
     * 生成验证码图片
     */
    public BufferedImage generateImage() {
        // 创建图片缓冲区
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        
        // 设置抗锯齿
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 绘制背景
        g.setColor(getRandomColor(240, 255));
        g.fillRect(0, 0, width, height);

        // 绘制边框
        g.setColor(getRandomColor(180, 200));
        g.drawRect(0, 0, width - 1, height - 1);

        // 生成验证码内容
        generateCode();

        // 绘制验证码
        drawCode(g);

        // 绘制干扰线
        draw干扰线(g);

        // 绘制干扰点
        draw干扰点(g);

        // 释放资源
        g.dispose();

        return image;
    }

    /**
     * 生成验证码内容
     */
    private void generateCode() {
        if (type == TYPE_ARITHMETIC) {
            generateArithmeticCode();
        } else {
            generateCharCode();
        }
    }

    /**
     * 生成字符型验证码
     */
    private void generateCharCode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < charCount; i++) {
            int index = random.nextInt(charSet.length());
            sb.append(charSet.charAt(index));
        }
        this.code = sb.toString();
    }

    /**
     * 生成算术型验证码
     */
    private void generateArithmeticCode() {
        int num1 = random.nextInt(10) + 1;
        int num2 = random.nextInt(10) + 1;
        int operator = random.nextInt(3);

        switch (operator) {
            case 0: // 加法
                arithmeticResult = num1 + num2;
                code = num1 + " + " + num2 + " = ?";
                break;
            case 1: // 减法（确保结果非负）
                if (num1 < num2) {
                    int temp = num1;
                    num1 = num2;
                    num2 = temp;
                }
                arithmeticResult = num1 - num2;
                code = num1 + " - " + num2 + " = ?";
                break;
            case 2: // 乘法
                num1 = random.nextInt(10) + 1;
                num2 = random.nextInt(10) + 1;
                arithmeticResult = num1 * num2;
                code = num1 + " × " + num2 + " = ?";
                break;
        }
    }

    /**
     * 绘制验证码
     */
    private void drawCode(Graphics2D g) {
        // 设置字体
        g.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 24 + random.nextInt(6)));

        if (type == TYPE_CHAR) {
            // 字符型验证码绘制
            int x = width / (charCount + 1);
            int y = height / 2 + 10;

            for (int i = 0; i < code.length(); i++) {
                // 随机颜色
                g.setColor(getRandomColor(30, 150));
                // 随机旋转角度
                int degree = random.nextInt(30) - 15;
                g.rotate(Math.toRadians(degree), x * (i + 1), y);
                g.drawString(String.valueOf(code.charAt(i)), x * (i + 1), y);
                g.rotate(-Math.toRadians(degree), x * (i + 1), y);
            }
        } else {
            // 算术型验证码绘制
            g.setColor(getRandomColor(30, 150));
            // 随机倾斜
            int shear = random.nextInt(3) - 1;
            g.shear(shear * 0.1, 0);
            g.drawString(code, 10, height / 2 + 10);
        }
    }

    /**
     * 绘制干扰线
     */
    private void draw干扰线(Graphics2D g) {
        for (int i = 0; i < lineCount; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            
            // 随机颜色和线宽
            g.setColor(getRandomColor(150, 200));
            g.setStroke(new BasicStroke(random.nextFloat() * 2 + 0.5f));
            g.drawLine(x1, y1, x2, y2);
        }
    }

    /**
     * 绘制干扰点
     */
    private void draw干扰点(Graphics2D g) {
        for (int i = 0; i < dotCount; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            
            // 随机颜色的点
            g.setColor(getRandomColor(100, 200));
            g.fillOval(x, y, 2, 2);
        }
    }

    /**
     * 生成随机颜色
     */
    private Color getRandomColor(int min, int max) {
        if (min > 255) min = 255;
        if (max > 255) max = 255;
        
        int r = min + random.nextInt(max - min);
        int g = min + random.nextInt(max - min);
        int b = min + random.nextInt(max - min);
        
        return new Color(r, g, b);
    }

    /**
     * 获取验证码图片的字节数组
     */
    public byte[] getImageData() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(generateImage(), "png", baos);
        return baos.toByteArray();
    }

    /**
     * 将验证码图片写入输出流
     */
    public void write(OutputStream os) throws IOException {
        ImageIO.write(generateImage(), "png", os);
    }

    // getter方法
    public String getCode() {
        return code;
    }

    public int getArithmeticResult() {
        return arithmeticResult;
    }

    // 测试方法
    public static void main(String[] args) {
        // 测试字符验证码
        CustomCaptchaGenerator charCaptcha = new CustomCaptchaGenerator(150, 50, CustomCaptchaGenerator.TYPE_CHAR);
        try {
            charCaptcha.write(new java.io.FileOutputStream("char_captcha.png"));
            System.out.println("字符验证码已生成，正确答案: " + charCaptcha.getCode());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 测试算术验证码
        CustomCaptchaGenerator arithmeticCaptcha = new CustomCaptchaGenerator(180, 50, CustomCaptchaGenerator.TYPE_ARITHMETIC);
        try {
            arithmeticCaptcha.write(new java.io.FileOutputStream("arithmetic_captcha.png"));
            System.out.println("算术验证码已生成，正确答案: " + arithmeticCaptcha.getArithmeticResult());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
    