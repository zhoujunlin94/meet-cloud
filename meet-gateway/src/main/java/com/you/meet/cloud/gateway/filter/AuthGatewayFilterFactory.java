package com.you.meet.cloud.gateway.filter;

/**
 * @author zhoujunlin
 * @date 2023年03月16日 17:21
 * @desc
 */
public class AuthGatewayFilterFactory {


    public static class Config {
        private String tokenHeaderName = "";
        private String userIdHeaderName = "";
    }

}
