package com.you.meet.cloud.lib.api.common.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.you.meet.cloud.lib.api.common.constant.CommonConstant;

import java.util.Date;

/**
 * @author zhoujl
 * @date 2022/3/12 15:41
 * @desc
 */
public final class RequestIdUtil {


    public static String requestId() {
        return DateUtil.format(new Date(), CommonConstant.Time.YYYYMMDD_HHMMSS_FMT)
                + RandomUtil.randomNumbers(6);
    }

}
