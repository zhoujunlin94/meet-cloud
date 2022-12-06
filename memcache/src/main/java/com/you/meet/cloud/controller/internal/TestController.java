package com.you.meet.cloud.controller.internal;

import com.you.meet.cloud.config.ServiceConfig;
import com.you.meet.nice.lib.common.pojo.JsonResponse;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhoujunlin
 * @date 2022年12月06日 11:02
 * @desc
 */
@RestController
public class TestController {

    @Resource
    private ServiceConfig serviceConfig;

    @GetMapping("/echo")
    public JsonResponse echo() {
        return JsonResponse.ok().setData(serviceConfig.getName());
    }

}
