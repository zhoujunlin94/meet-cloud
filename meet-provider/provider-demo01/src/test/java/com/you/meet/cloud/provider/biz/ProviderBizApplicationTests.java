package com.you.meet.cloud.provider.biz;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "dev")
@SpringBootTest(classes = ProviderDemo01Application.class)
class ProviderBizApplicationTests {

    @Test
    void contextLoads() {
    }

}
