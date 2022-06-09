package com.you.meet.nice.starter.ws.pojo;

import com.you.meet.nice.lib.common.util.JsonUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author zhoujl
 * @date 2021/5/14 10:40
 * @desc
 */
@Data
@NoArgsConstructor
@ToString
public class MessageDto implements Serializable {

    /**
     * 源客户端用户名
     */
    private String sourceUserName;

    /**
     * 目标客户端用户名
     */
    private String targetUserName;

    /**
     * 消息类型
     */
    private String msgType;

    /**
     * 消息内容
     */
    private String msgContent;

    public MessageDto(String sourceUserName, String targetUserName, String msgType, String msgContent) {
        this.sourceUserName = sourceUserName;
        this.targetUserName = targetUserName;
        this.msgType = msgType;
        this.msgContent = msgContent;
    }

    public MessageDto(String sourceUserName, String msgType, String msgContent) {
        this.sourceUserName = sourceUserName;
        this.msgType = msgType;
        this.msgContent = msgContent;
    }

    public String jsonStr() {
        return JsonUtil.parseObj2Str(this);
    }
}
