package com.wanjia.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author JT.L
 * @date 2020/5/25 14:53:02
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SmsTest {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void testSend() throws InterruptedException {
        Map<String, String> msg = new HashMap<>();
        msg.put("phone", "18628362825");
        msg.put("code", "652314");
        amqpTemplate.convertAndSend("wj.sms.exchange", "sms.verify.code", msg);
        Thread.sleep(10000L);
    }
}
