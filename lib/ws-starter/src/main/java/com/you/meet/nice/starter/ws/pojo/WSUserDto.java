package com.you.meet.nice.starter.ws.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhoujl
 * @date 2021/5/16 11:51
 * @desc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WSUserDto {

    private String userName;

    private String sessionId;

    private String serverId;

}
