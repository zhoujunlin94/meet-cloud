package com.you.meet.cloud.common.util;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.RandomUtil;

import java.util.Date;

/**
 * @author zhoujl
 * @date 2022/3/12 15:41
 * @desc
 */
public final class RequestIdUtil {

    public static final String REQUEST_ID = "requestId";

    public static String requestId() {
        return DatePattern.PURE_DATETIME_MS_FORMAT.format(new Date()) + RandomUtil.randomNumbers(6);
    }

}
