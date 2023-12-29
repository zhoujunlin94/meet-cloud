package com.you.meet.cloud.provider.kafka.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @author zhoujunlin
 * @date 2023年12月29日 9:56
 * @desc
 */
@Data
public class BaseMessageDTO {

    private String bizId;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
