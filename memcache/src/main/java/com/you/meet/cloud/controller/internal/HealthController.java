package com.you.meet.cloud.controller.internal;

import cn.hutool.core.date.DateUtil;
import com.you.meet.nice.lib.common.pojo.JsonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhoujunlin
 * @date 2022年06月11日 22:05
 * @desc
 */
@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping("/okStr")
    public String okStr() {
        return "ok";
    }

    @GetMapping("/okJson")
    public JsonResponse okJson() {
        return JsonResponse.ok(DateUtil.date());
    }

}
