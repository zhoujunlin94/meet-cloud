package com.you.meet.cloud.consumer.biz;

import com.you.meet.cloud.consumer.ConsumerApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "dev")
@SpringBootTest(classes = ConsumerApplication.class)
class ConsumerApplicationTests {

    @Test
    void contextLoads() {
    }

}
