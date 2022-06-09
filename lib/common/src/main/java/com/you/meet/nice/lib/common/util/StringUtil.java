package com.you.meet.nice.lib.common.util;

import cn.hutool.core.util.StrUtil;

import java.util.Arrays;

/**
 * @author zhoujl
 * @date 2020/5/5 13:52
 * @desc
 **/
public class StringUtil {

    /**
     * 判断字符串中是否存在子串
     *
     * @param str    以分隔符组成的字符串  默认逗号分隔
     * @param subStr 子串
     * @return
     */
    public static boolean contains(String str, String subStr) {
        return contains(",", str, subStr);
    }

    /**
     * 判断字符串中是否存在子串
     *
     * @param splitKey 分隔符
     * @param str      以分隔符组成的字符串
     * @param subStr   子串
     * @return
     */
    public static boolean contains(String splitKey, String str, String subStr) {
        if (StrUtil.isBlank(str) || StrUtil.isBlank(subStr) || StrUtil.isBlank(splitKey)) {
            return false;
        }
        return Arrays.asList(str.split(splitKey)).contains(subStr);
    }
}
