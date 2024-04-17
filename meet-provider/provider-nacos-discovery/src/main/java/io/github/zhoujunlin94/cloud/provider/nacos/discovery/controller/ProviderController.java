package io.github.zhoujunlin94.cloud.provider.nacos.discovery.controller;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoujunlin
 * @date 2023年12月26日 20:05
 * @desc
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/provider")
public class ProviderController {

    private final NacosDiscoveryProperties nacosDiscoveryProperties;

    @GetMapping("/headers")
    public Map<String, String> getHeader(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();

        Map<String, String> ret = new HashMap<>();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            StringBuilder headerValue = new StringBuilder();
            Enumeration<String> headerValues = request.getHeaders(headerName);
            while (headerValues.hasMoreElements()) {
                headerValue.append(headerValues.nextElement()).append(" ");
            }
            ret.put(headerName, headerValue.toString());
        }

        return ret;
    }

    @GetMapping("/sleep")
    public String sleep() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "ok";
    }

    @GetMapping("/echo/{string}")
    public String echo(@PathVariable String string) {
        return "hello Nacos Discovery " + string;
    }

    @GetMapping("/divide")
    public String divide(@RequestParam Integer a, @RequestParam Integer b) {
        if (b == 0) {
            return String.valueOf(0);
        } else {
            return String.valueOf(a / b);
        }
    }

    @GetMapping("/nacosDiscoveryProperties")
    public NacosDiscoveryProperties nacosDiscoveryProperties() {
        return nacosDiscoveryProperties;
    }

}
