package com.you.meet.cloud.lib.api.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;

/**
 * @author zhoujl
 * @date 2021/5/6 17:38
 * @desc 加密工具类
 */
@Slf4j
public class EncryptUtil {


    public static String MD5Str(String srcStr) {
        return DigestUtils.md5DigestAsHex(srcStr.getBytes());
    }


}
