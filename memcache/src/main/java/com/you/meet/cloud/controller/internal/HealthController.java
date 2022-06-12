package com.you.meet.cloud.controller.internal;

import cn.hutool.core.date.DateUtil;
import com.you.meet.cloud.lib.api.common.pojo.JsonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhoujunlin
 * @date 2022年06月11日 22:05
 * @desc
 */
@RestController
public class HealthController {

    @GetMapping("/health")
    public String health() {
        return "ok";
    }

    @GetMapping("/health2")
    public JsonResponse health2() {
        int i = 1 / 0;
        return JsonResponse.ok(DateUtil.date());
    }

}
