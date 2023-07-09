package com.you.meet.cloud.common.util;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.ImmutableMap;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.you.meet.cloud.common.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhoujl
 * @date 2021/3/19 14:53
 * @desc 二维码生成工具
 */
@Slf4j
public class QRCodeUtil {

    private static final String CHARSET = "utf-8";
    private static final String FORMAT_NAME = "jpg";
    private static final int QR_CODE_SIZE = 300;
    private static final int WIDTH = 60;
    private static final int HEIGHT = 60;


    /**
     * 生成二维码(内嵌LOGO)成base64字符串
     *
     * @param content      内容
     * @param imgPath      LOGO地址
     * @param needCompress 是否压缩LOGO
     * @throws Exception
     */
    public static String encodeAndBase64Str(String content, String imgPath, boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content, imgPath, needCompress);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, FORMAT_NAME, baos);
        //转换成base64串
        return CommonConstant.IMG_BASE64_PREFIX + Base64Encoder.encode(baos.toByteArray()).trim();
    }

    /**
     * 生成二维码(无LOGO)成base64字符串
     * 生成二维码(内嵌LOGO)到指定路径
     *
     * @param content 内容
     * @return
     * @throws Exception
     */
    public static String encodeAndBase64Str(String content) throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content, null, Boolean.FALSE);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, FORMAT_NAME, baos);
        //转换成base64串
        return CommonConstant.IMG_BASE64_PREFIX + Base64Encoder.encode(baos.toByteArray()).trim();
    }

    /**
     * 解析base64Str
     *
     * @param base64Str
     * @return
     * @throws Exception
     */
    public static String decodeBase64Str(String base64Str) throws Exception {
        // 图像数据为空
        if (StrUtil.isBlank(base64Str)) {
            return null;
        }
        if (base64Str.contains(CommonConstant.IMG_BASE64_PREFIX)) {
            base64Str = StrUtil.sub(base64Str, 23, -1);
        }
        // Base64解码,对字节数组字符串进行Base64解码并生成图片
        byte[] b = Base64Decoder.decode(base64Str);
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {
                // 调整异常数据
                b[i] += 256;
            }
        }
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(b));
        if (Objects.isNull(image)) {
            return null;
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        Map<DecodeHintType, Object> hints = ImmutableMap.of(DecodeHintType.CHARACTER_SET, CHARSET);
        result = new MultiFormatReader().decode(bitmap, hints);
        return result.getText();
    }

    /**
     * 解析二维码
     *
     * @param file 二维码图片
     * @return
     * @throws Exception
     */
    public static String decode(File file) throws Exception {
        BufferedImage image = ImageIO.read(file);
        if (Objects.isNull(image)) {
            return null;
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        Map<DecodeHintType, Object> hints = ImmutableMap.of(DecodeHintType.CHARACTER_SET, CHARSET);
        result = new MultiFormatReader().decode(bitmap, hints);
        return result.getText();
    }

    /**
     * 解析二维码
     *
     * @param path 二维码图片地址
     * @return 不是二维码的内容返回null, 是二维码直接返回识别的结果
     * @throws Exception
     */
    public static String decode(String path) throws Exception {
        return QRCodeUtil.decode(new File(path));
    }

    /**
     * 生成二维码(内嵌LOGO)到指定路径
     *
     * @param content      内容
     * @param imgPath      LOGO地址
     * @param destPath     存放目录
     * @param needCompress 是否压缩LOGO
     * @throws Exception
     */
    public static void encode(String content, String imgPath, String destPath,
                              boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content, imgPath, needCompress);
        mkdirs(destPath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, FORMAT_NAME, baos);
        //转换成base64串
        String jpgBase64 = Base64Encoder.encode(baos.toByteArray()).trim();
        String file = EncryptUtil.encryptMD5(jpgBase64) + ".jpg";
        ImageIO.write(image, FORMAT_NAME, new File(destPath + "/" + file));
    }

    /**
     * 生成二维码(内嵌LOGO) -  压缩
     *
     * @param content  内容
     * @param imgPath  LOGO地址
     * @param destPath 存储地址
     * @throws Exception
     */
    public static void encode(String content, String imgPath, String destPath)
            throws Exception {
        QRCodeUtil.encode(content, imgPath, destPath, Boolean.TRUE);
    }

    /**
     * 生成二维码（无logo）
     *
     * @param content      内容
     * @param destPath     存储地址
     * @param needCompress 是否压缩LOGO
     * @throws Exception
     */
    public static void encode(String content, String destPath,
                              boolean needCompress) throws Exception {
        QRCodeUtil.encode(content, null, destPath, needCompress);
    }

    /**
     * 生成二维码(无logo)
     *
     * @param content  内容
     * @param destPath 存储地址
     * @throws Exception
     */
    public static void encode(String content, String destPath) throws Exception {
        QRCodeUtil.encode(content, null, destPath, Boolean.FALSE);
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content      内容
     * @param imgPath      LOGO地址
     * @param output       输出流
     * @param needCompress 是否压缩LOGO
     * @throws Exception
     */
    public static void encode(String content, String imgPath,
                              OutputStream output, boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content, imgPath,
                needCompress);
        ImageIO.write(image, FORMAT_NAME, output);
    }

    /**
     * 生成二维码 无logo
     *
     * @param content 内容
     * @param output  输出流
     * @throws Exception
     */
    public static void encode(String content, OutputStream output)
            throws Exception {
        QRCodeUtil.encode(content, null, output, false);
    }


    // ====================== 私有方法

    /**
     * 生成二维码的方法
     *
     * @param content      目标URL
     * @param imgPath      LOGO图片地址
     * @param needCompress 是否压缩LOGO
     * @return 二维码图片
     * @throws Exception
     */
    private static BufferedImage createImage(String content, String imgPath,
                                             boolean needCompress) throws Exception {
        Map<EncodeHintType, Object> hints = ImmutableMap.of(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H,
                EncodeHintType.CHARACTER_SET, CHARSET,
                EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, QR_CODE_SIZE, QR_CODE_SIZE, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000
                        : 0xFFFFFFFF);
            }
        }
        if (StrUtil.isBlank(imgPath)) {
            return image;
        }
        // 插入logo图片
        QRCodeUtil.insertImage(image, imgPath, needCompress);
        return image;
    }

    /**
     * 插入LOGO
     *
     * @param source       二维码图片
     * @param imgPath      LOGO图片地址
     * @param needCompress 是否压缩
     * @throws Exception
     */
    private static void insertImage(BufferedImage source, String imgPath,
                                    boolean needCompress) throws Exception {
        File file = new File(imgPath);
        if (!file.exists()) {
            log.info("该文件{}不存在！", imgPath);
            return;
        }
        Image src = ImageIO.read(new File(imgPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) {
            // 压缩LOGO
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }
            Image image = src.getScaledInstance(width, height,
                    Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            // 绘制缩小后的图
            g.drawImage(image, 0, 0, null);
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QR_CODE_SIZE - width) / 2;
        int y = (QR_CODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
     *
     * @param destPath 存放目录
     */
    private static boolean mkdirs(String destPath) {
        File file = new File(destPath);
        if (!file.exists() && !file.isDirectory()) {
            return file.mkdirs();
        }
        return false;
    }
}
