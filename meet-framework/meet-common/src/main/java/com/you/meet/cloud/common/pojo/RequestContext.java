package com.you.meet.cloud.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhoujunlin
 * @date 2023年03月14日 17:36
 * @desc
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestContext {

    private String requestId;

    private String clientIP;

}
