package com.you.meet.nice.starter.redis.pojo.queue;

import com.you.meet.nice.lib.common.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhoujl
 * @date 2021/3/5 14:52
 * @desc 队列消息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskItem {

    private String id;

    private String handlerName;

    private Object msg;

    @Override
    public String toString() {
        return JsonUtil.parseObj2Str(this);
    }
}
