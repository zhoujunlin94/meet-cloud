package com.you.meet.cloud.common.util;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhoujl
 * @date 2021/5/6 17:38
 * @desc 加密工具类
 */
@Slf4j
public class EncryptUtil {

    public static String encryptMD5(String srcStr) {
        return MD5.create().digestHex(srcStr);
    }

    public static String encryptBASE64(byte[] key) throws Exception {
        return Base64Encoder.encode(key).replace(StrUtil.CR, StrUtil.EMPTY).replace(StrUtil.LF, StrUtil.EMPTY);
    }

}
