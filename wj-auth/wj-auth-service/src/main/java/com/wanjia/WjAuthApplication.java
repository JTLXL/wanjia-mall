package com.wanjia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author JT.L
 * @date 2020/5/29 12:21:37
 * @description
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class WjAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(WjAuthApplication.class);
    }
}
