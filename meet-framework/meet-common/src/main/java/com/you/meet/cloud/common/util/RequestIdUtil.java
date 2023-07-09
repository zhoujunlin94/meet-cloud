package com.you.meet.cloud.common.util;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.you.meet.cloud.common.pojo.RequestContext;

import java.util.Date;
import java.util.Objects;

/**
 * @author zhoujl
 * @date 2022/3/12 15:41
 * @desc
 */
public final class RequestIdUtil {

    public static final String REQUEST_ID = "requestId";

    public static String generateRequestId() {
        return DatePattern.PURE_DATETIME_MS_FORMAT.format(new Date()) + RandomUtil.randomNumbers(6);
    }

    public static String getRequestId() {
        String requestId = StrUtil.EMPTY;
        RequestContext requestContext = RequestContextUtil.get();
        if (Objects.nonNull(requestContext)) {
            requestId = requestContext.getRequestId();
        }
        if (StrUtil.isBlank(requestId)) {
            requestId = generateRequestId();
        }
        return requestId;
    }

}
