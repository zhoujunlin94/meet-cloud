package com.you.meet.cloud.consumer.biz;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author zhoujunlin
 * @date 2023年03月07日 15:24
 * @desc
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class JasyptTest {
    @Resource
    private StringEncryptor stringEncryptor;

    @Value("${mima:}")
    private String value;

    @Test
    public void encode() {
        String encrypt = stringEncryptor.encrypt("mima");
        // USOt4pNHly1XQK+64BCAst2Rk7gSzoXG  把这个放到nacos中配置起来  ENC(USOt4pNHly1XQK+64BCAst2Rk7gSzoXG)
        System.out.println("加密：" + encrypt);
        System.out.println("解密：" + stringEncryptor.decrypt(encrypt));

        // sTOLBlxfq3m4fCLuGNChnIBCdenciAmk
        System.out.println("加密2：" + stringEncryptor.encrypt("mima2"));
    }

    @Test
    public void getValue() {
        // 获取到是解密后的数据
        System.out.println(value);
    }

}
