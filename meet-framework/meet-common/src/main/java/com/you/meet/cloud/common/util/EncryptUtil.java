package com.you.meet.cloud.common.util;

import cn.hutool.crypto.digest.MD5;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhoujl
 * @date 2021/5/6 17:38
 * @desc 加密工具类
 */
@Slf4j
public class EncryptUtil {


    public static String MD5Str(String srcStr) {
        return MD5.create().digestHex(srcStr);
    }


}
