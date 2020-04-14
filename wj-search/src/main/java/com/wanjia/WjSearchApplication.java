package com.wanjia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author JT.L
 * @date 2020/4/10 16:09:00
 * @description
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class WjSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(WjSearchApplication.class);
    }
}


