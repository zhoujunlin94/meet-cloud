package com.you.meet.nice.starter.redis.util;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.util.StrUtil;
import com.you.meet.nice.lib.common.constant.CommonConstant;
import com.you.meet.nice.starter.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhoujl
 * @date 2021/5/7 9:52
 * @desc
 */
@Slf4j
public class ImageCodeUtil {

    private static final int WIDTH = 130;
    private static final int HEIGHT = 38;
    private static final int CODE_COUNT = 4;
    private static final int CIRCLE_COUNT = 8;

    public static String imgBase64Code(String redisKey, RedisService redisService) {
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(WIDTH, HEIGHT, CODE_COUNT, CIRCLE_COUNT);
        // 验证码
        String verifyCode = captcha.getCode();
        log.info("获取验证码:{},redisKey:{}", verifyCode, redisKey);
        // 将验证码放入redis(先删除，后保存)
        redisService.remove(redisKey);
        redisService.set(redisKey, verifyCode, 600L);
        return CommonConstant.IMG_BASE64_PREFIX + captcha.getImageBase64();
    }

    public static boolean checkValidCode(String redisKey, String validCode, RedisService redisService) {
        String codeInRedis = redisService.get(redisKey, String.class);
        return StrUtil.equalsIgnoreCase(validCode, codeInRedis);
    }
}
